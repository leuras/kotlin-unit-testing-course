package br.com.leuras.core.business.pipeline.impl

import br.com.leuras.core.business.pipeline.PipelineStep
import br.com.leuras.core.entity.CustomerTradingOrder
import br.com.leuras.core.enums.OrderStatus
import br.com.leuras.core.port.CustomerSharesDetailRepository
import java.time.LocalDateTime

class IncreaseCustomerSharesStep(
    private val repository: CustomerSharesDetailRepository): PipelineStep {

    override fun execute(input: Any): Any {
        val order = input as CustomerTradingOrder

        val newShares = order.orderDetail.toShares()
        val newSharesBalance = order.sharesDetail
            ?.let { it + newShares }
                ?: newShares

        this.repository.update(order.customer.customerId, newSharesBalance)

        return order.copy(
            sharesDetail = newSharesBalance,
            orderDetail = order.orderDetail.copy(
                status = OrderStatus.EXECUTED,
                updatedAt = LocalDateTime.now()
            )
        )
    }
}
