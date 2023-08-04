package br.com.leuras.core.port

import br.com.leuras.core.entity.CustomerTradingOrder
import br.com.leuras.core.entity.OrderDetail

interface CustomerOrderRepository {
    fun find(orderId: String): CustomerTradingOrder?
    fun findOwnedBy(customerId: String): List<OrderDetail>
    fun update(customerOrder: CustomerTradingOrder): CustomerTradingOrder
}
