package br.com.leuras.core.entity

import java.time.LocalDateTime

data class SharesDetail(
    val tickerSymbol: String,
    val shares: Int,
    val averagePrice: Double,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()) {

    operator fun plus(other: SharesDetail): SharesDetail {
        val balance = this.shares * this.averagePrice
        val bought = other.shares * other.averagePrice
        val quantity = this.shares + other.shares

        val newBalance = balance + bought
        val averagePrice = (newBalance) / quantity

        return this.copy(
            averagePrice = averagePrice,
            shares = quantity,
            updatedAt = LocalDateTime.now()
        )
    }
}
