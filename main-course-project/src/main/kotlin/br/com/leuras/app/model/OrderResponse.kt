package br.com.leuras.app.model

import br.com.leuras.core.enums.OrderOperation
import br.com.leuras.core.enums.OrderStatus
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDate
import java.time.LocalDateTime

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class OrderResponse(
    val orderId: String,
    val customerId: String,
    val tickerSymbol: String,
    val operation: OrderOperation,
    val quantity: Int,
    val unitPrice: Double,
    val amount: Double,
    val tax: Double,
    val brokerageFee: Double,
    val status: OrderStatus,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val dueAt: LocalDate
)