package br.com.leuras.core.entity

import java.time.LocalDateTime

data class SharesDetail(
    val tickerSymbol: String,
    val shares: Int,
    val averagePrice: Double,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()) {

    operator fun plus(order: OrderDetail): SharesDetail {
        val balance = this.shares * this.averagePrice
        val bought = order.quantity * order.unitPrice
        val quantity = this.shares + order.quantity

        val newBalance = balance + bought
        val averagePrice = (newBalance) / quantity

        return this.copy(
            averagePrice = averagePrice,
            shares = quantity,
            updatedAt = LocalDateTime.now()
        )
    }
}
