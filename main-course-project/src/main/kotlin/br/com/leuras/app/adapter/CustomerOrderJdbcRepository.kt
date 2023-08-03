package br.com.leuras.app.adapter

import br.com.leuras.app.extension.toMap
import br.com.leuras.app.infrastructure.sql.CustomerOrderSQL
import br.com.leuras.core.entity.CustomerTradingOrder
import br.com.leuras.core.entity.OrderDetail
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

    override fun find(orderId: String): CustomerTradingOrder {
        TODO("Not yet implemented")
    }

    override fun findOwnedBy(customerId: String): List<OrderDetail> {
        val orders = this.jdbcTemplate.query(
            CustomerOrderSQL.SELECT,
            PreparedStatementSetter { it.setString(1, customerId) },
            RowMapper { rs, _ ->
                rs.getString("orders")
            }).firstOrNull()

        return orders?.let {
            this.objectMapper.readValue(
                orders,
                this.objectMapper.typeFactory.constructCollectionType(List::class.java, OrderDetail::class.java)
            )
        } ?: emptyList()
    }

    override fun update(customerOrder: CustomerTradingOrder): CustomerTradingOrder {
        val newOrders = this.findOwnedBy(customerOrder.customer.customerId)
            .toMutableList()
            .also { it.add(customerOrder.orderDetail) }

        this.jdbcTemplate.update(CustomerOrderSQL.UPDATE) {
            it.setString(1, this.objectMapper.writeValueAsString(newOrders.toMap()))
            it.setString(2, customerOrder.customer.customerId)
        }

        return customerOrder
    }

    override fun execute(customerOrder: CustomerTradingOrder): CustomerTradingOrder {
        TODO("Not yet implemented")
    }

    override fun cancel(customerOrder: CustomerTradingOrder): CustomerTradingOrder {
        TODO("Not yet implemented")
    }
}
