package br.com.leuras.core.entity

import br.com.leuras.core.enums.BrokerageFee
import br.com.leuras.core.enums.OrderOperation
import br.com.leuras.core.enums.OrderStatus
import java.time.LocalDateTime
import java.util.UUID

data class OrderDetail(
    val orderId: String = UUID.randomUUID().toString(),
    val tickerSymbol: String,
    val operation: OrderOperation,
    val quantity: Int,
    val unitPrice: Double,
    val tax: Double = 0.0,
    val brokerageFee: Double = BrokerageFee.REGULAR.fee,
    val status: OrderStatus,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime) {

    fun toShares() = SharesDetail(
        tickerSymbol = this.tickerSymbol,
        shares = this.quantity,
        averagePrice = ((this.quantity * this.unitPrice) + this.brokerageFee) / this.quantity,
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now()
    )
}
