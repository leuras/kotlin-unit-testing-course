package br.com.leuras.app.adapter

import br.com.leuras.app.extension.readJSONList
import br.com.leuras.app.extension.toMap
import br.com.leuras.app.infrastructure.sql.CustomerSharesDetailSQL
import br.com.leuras.core.entity.SharesDetail
import br.com.leuras.core.port.CustomerSharesDetailRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementSetter
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class CustomerSharesDetailJdbcRepository(
    private val objectMapper: ObjectMapper,
    private val jdbcTemplate: JdbcTemplate): CustomerSharesDetailRepository {

    override fun update(customerId: String, shares: SharesDetail) {
        val newShares = this.listSharesOf(customerId)
            .filter { it.tickerSymbol != shares.tickerSymbol }
            .toMutableList()
            .also { it.add(shares) }

        this.jdbcTemplate.update(CustomerSharesDetailSQL.UPDATE) {
            it.setString(1, this.objectMapper.writeValueAsString(newShares.toMap()))
            it.setString(2, customerId)
        }
    }

    private fun listSharesOf(customerId: String): List<SharesDetail> {
        return this.jdbcTemplate.query(
            CustomerSharesDetailSQL.SELECT,
            PreparedStatementSetter { it.setString(1, customerId) },
            RowMapper { rs, _ ->
                rs.getString("shares")
                this.objectMapper.readJSONList<SharesDetail>(rs.getString("shares"))
            }).flatten()
    }
}
