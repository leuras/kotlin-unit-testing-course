package br.com.leuras.core.entity

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
    val tax: Double? = null,
    val brokerageFee: Double? = null,
    val status: OrderStatus,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
