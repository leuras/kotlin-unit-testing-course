package br.com.leuras.app.model

import br.com.leuras.core.enums.OrderOperation
import br.com.leuras.core.enums.OrderStatus
import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class OrderConfirmationResponse(
    val orderId: String,
    val stock: String,
    val operation: OrderOperation,
    val quantity: Int,
    val unitPrice: Double,
    val amount: Double,
    val status: OrderStatus,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
