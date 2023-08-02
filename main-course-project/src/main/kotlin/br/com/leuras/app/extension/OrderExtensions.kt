package br.com.leuras.app.extension

import br.com.leuras.app.model.OrderConfirmationResponse
import br.com.leuras.core.entity.OrderDetail
import br.com.leuras.core.entity.TradingOrder

fun TradingOrder.toConfirmationResponse() = OrderConfirmationResponse(
    orderId = this.order.orderId,
    stock = this.order.tickerSymbol,
    operation = this.order.operation,
    quantity = this.order.quantity,
    unitPrice = this.order.unitPrice,
    amount = this.order.quantity * this.order.unitPrice,
    status = this.order.status,
    createdAt = this.order.createdAt,
    updatedAt = this.order.updatedAt
)
fun List<OrderDetail>.toMap() = this.map {
    mapOf(
        "orderId" to it.orderId,
        "tickerSymbol" to it.tickerSymbol,
        "operation" to it.operation.name,
        "quantity" to it.quantity,
        "unitPrice" to it.unitPrice,
        "tax" to it.tax,
        "brokerageFee" to it.brokerageFee,
        "status" to it.status,
        "createdAt" to it.createdAt,
        "updatedAt" to it.updatedAt
    )
}
