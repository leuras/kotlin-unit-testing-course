package br.com.leuras.core.port

import br.com.leuras.core.entity.CustomerTradingOrder

interface CustomerOrderRepository {
    fun find(orderId: String): CustomerTradingOrder
    fun register(customerOrder: CustomerTradingOrder): CustomerTradingOrder
    fun execute(customerOrder: CustomerTradingOrder): CustomerTradingOrder
    fun cancel(customerOrder: CustomerTradingOrder): CustomerTradingOrder
}
