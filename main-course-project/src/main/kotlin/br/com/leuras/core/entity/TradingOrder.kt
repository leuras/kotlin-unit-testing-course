package br.com.leuras.core.entity

data class TradingOrder(
    val customerId: String,
    val order: OrderDetail
)
