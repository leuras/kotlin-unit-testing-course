package br.com.leuras.core.entity

data class CustomerAccount(
    val customer: Customer,
    val sharesDetails: List<SharesDetail>,
    val ordersDetails: List<OrderDetail>,
    val profit: Double = 0.0,
    val prejudiceBalance: Double = 0.0) {

    val balance: Double
        get() = this.sharesDetails.map { it.shares * it.averagePrice }
            .reduce { accumulator, balance -> accumulator + balance }

    operator fun plus(tradingOrder: TradingOrder) = CustomerTradingOrder(
        customer = this.customer,
        sharesDetail = this.sharesDetails.firstOrNull { it.tickerSymbol == tradingOrder.order.tickerSymbol },
        orderDetail = tradingOrder.order
    )
}
