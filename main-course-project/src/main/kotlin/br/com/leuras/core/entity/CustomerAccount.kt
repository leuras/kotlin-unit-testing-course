package br.com.leuras.core.entity

import java.time.LocalDateTime

data class CustomerAccount(
    val customer: Customer,
    val sharesDetails: List<SharesDetail>,
    val ordersDetails: List<OrderDetail>,
    val losses: Double = 0.0,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()) {

    val balance: Double
        get() = this.sharesDetails.map { it.shares * it.averagePrice }
            .reduce { accumulator, balance -> accumulator + balance }

    operator fun plus(tradingOrder: TradingOrder) = CustomerTradingOrder(
        customer = this.customer,
        sharesDetail = this.sharesDetails.firstOrNull { it.tickerSymbol == tradingOrder.orderDetail.tickerSymbol },
        orderDetail = tradingOrder.orderDetail
    )
}
