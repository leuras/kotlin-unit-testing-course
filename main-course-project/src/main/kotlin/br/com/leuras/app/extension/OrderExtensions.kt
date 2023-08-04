package br.com.leuras.app.extension

import br.com.leuras.app.model.OrderConfirmationResponse
import br.com.leuras.core.entity.CustomerTradingOrder
import br.com.leuras.core.entity.OrderDetail
import br.com.leuras.core.entity.TradingOrder

fun TradingOrder.toConfirmationResponse() = this.order.toConfirmationResponse()

fun CustomerTradingOrder.toConfirmationResponse() = this.orderDetail.toConfirmationResponse()

fun OrderDetail.toConfirmationResponse() = OrderConfirmationResponse(
    orderId = this.orderId,
    stock = this.tickerSymbol,
    operation = this.operation,
    quantity = this.quantity,
    unitPrice = this.unitPrice,
    amount = this.quantity * this.unitPrice,
    status = this.status,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
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
