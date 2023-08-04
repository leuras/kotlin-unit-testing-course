package br.com.leuras.app.adapter

import br.com.leuras.app.extension.readJSONList
import br.com.leuras.app.extension.toMap
import br.com.leuras.app.infrastructure.sql.CustomerOrderSQL
import br.com.leuras.core.entity.Customer
import br.com.leuras.core.entity.CustomerTradingOrder
import br.com.leuras.core.entity.OrderDetail
import br.com.leuras.core.entity.SharesDetail
import br.com.leuras.core.port.CustomerOrderRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementSetter
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class CustomerOrderJdbcRepository(
    private val objectMapper: ObjectMapper,
    private val jdbcTemplate: JdbcTemplate): CustomerOrderRepository {

    override fun find(orderId: String): CustomerTradingOrder? {
        return this.jdbcTemplate.query(
            CustomerOrderSQL.SELECT_CUSTOMER_ORDERS_BY_ID,
            PreparedStatementSetter { it.setString(1, orderId) },
            RowMapper { rs, _ ->
                with (this.objectMapper) {
                    val order = readJSONList<OrderDetail>(rs.getString("orders"))
                        .first { it.orderId == orderId }

                    val shares = readJSONList<SharesDetail>(rs.getString("shares"))
                        .firstOrNull { it.tickerSymbol == order.tickerSymbol }

                    CustomerTradingOrder(
                        customer = Customer(
                            customerId = rs.getString("customer_id"),
                            customerName = rs.getString("customer_name"),
                            qualifiedInvestor = rs.getBoolean("qualified_investor")
                        ),
                        sharesDetail = shares,
                        orderDetail  = order
                    )
                }
            }
        ).firstOrNull()
    }

    override fun findOwnedBy(customerId: String): List<OrderDetail> {
        return this.jdbcTemplate.query(
            CustomerOrderSQL.SELECT_ORDERS_BY_CUSTOMER,
            PreparedStatementSetter { it.setString(1, customerId) },
            RowMapper { rs, _ ->
                this.objectMapper.readJSONList<OrderDetail>(rs.getString("orders"))
            }
        ).flatten()
    }

    override fun update(customerOrder: CustomerTradingOrder): CustomerTradingOrder {
        val newOrders = this.findOwnedBy(customerOrder.customer.customerId)
            .filter { it.orderId != customerOrder.orderDetail.orderId }
            .toMutableList()
            .also { it.add(customerOrder.orderDetail) }

        this.jdbcTemplate.update(CustomerOrderSQL.UPDATE) {
            it.setString(1, this.objectMapper.writeValueAsString(newOrders.toMap()))
            it.setString(2, customerOrder.customer.customerId)
        }

        return customerOrder
    }
}
