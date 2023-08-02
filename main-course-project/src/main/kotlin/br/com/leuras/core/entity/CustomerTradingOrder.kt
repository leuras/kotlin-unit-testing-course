package br.com.leuras.core.entity

data class CustomerTradingOrder(
    val customer: Customer,
    val shares: SharesDetail?,
    val order: OrderDetail
)
