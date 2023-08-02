package br.com.leuras.core.entity

data class CustomerAccount(
    val customer: Customer,
    val shares: List<SharesDetail>,
    val orders: List<OrderDetail>,
    val profit: Double = 0.0,
    val prejudiceBalance: Double = 0.0) {

    val balance: Double
        get() = this.shares.map { it.shares * it.averagePrice }
            .reduce { accumulator, balance -> accumulator + balance }

    operator fun plus(tradingOrder: TradingOrder) = CustomerTradingOrder(
        customer = this.customer,
        shares = this.shares.firstOrNull { it.tickerSymbol == tradingOrder.order.tickerSymbol },
        order = tradingOrder.order
    )
}
