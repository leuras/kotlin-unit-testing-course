package br.com.leuras.app.model

import br.com.leuras.core.entity.OrderDetail
import br.com.leuras.core.entity.TradingOrder
import br.com.leuras.core.enums.OrderOperation
import br.com.leuras.core.enums.OrderStatus
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDateTime

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class OrderRequest(
    val customerId: String,
    val tickerSymbol: String,
    val operation: OrderOperation,
    val quantity: Int,
    val unitPrice: Double) {

    fun toTradingOrder() = TradingOrder(
        customerId = this.customerId,
        order = OrderDetail(
            tickerSymbol = this.tickerSymbol,
            operation = this.operation,
            quantity = this.quantity,
            unitPrice = this.unitPrice,
            status = OrderStatus.PENDING,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    )
}
