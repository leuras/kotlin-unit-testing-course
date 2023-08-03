package br.com.leuras.app.adapter

import br.com.leuras.app.extension.readJSONList
import br.com.leuras.app.infrastructure.sql.CustomerAccountSQL
import br.com.leuras.core.entity.Customer
import br.com.leuras.core.entity.CustomerAccount
import br.com.leuras.core.port.CustomerAccountRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementSetter
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class CustomerAccountJdbcRepository(
    private val objectMapper: ObjectMapper,
    private val jdbcTemplate: JdbcTemplate): CustomerAccountRepository {

    override fun find(customerId: String): CustomerAccount? {
        return this.jdbcTemplate.query(
            CustomerAccountSQL.SELECT,
            PreparedStatementSetter { it.setString(1, customerId) },
            RowMapper { rs, _ ->
                CustomerAccount(
                    customer = Customer(
                        customerId = rs.getString("customer_id"),
                        customerName = rs.getString("customer_name"),
                        qualifiedInvestor = rs.getBoolean("qualified_investor")
                    ),
                    sharesDetails = this.objectMapper.readJSONList(rs.getString("shares")),
                    ordersDetails = this.objectMapper.readJSONList(rs.getString("orders"))
                )
            }).firstOrNull()
    }
}