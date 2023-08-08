package br.com.leuras.core.entity

data class CustomerTradingOrder(
    val customer: Customer,
    val sharesDetail: SharesDetail?,
    val orderDetail: OrderDetail,
    val profit: Double = 0.0
)
