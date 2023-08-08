package br.com.leuras.core.business.pipeline.impl

import br.com.leuras.core.business.pipeline.PipelineStep
import br.com.leuras.core.entity.CustomerTradingOrder
import br.com.leuras.core.enums.OrderStatus
import br.com.leuras.core.port.CustomerSharesDetailRepository
import java.time.LocalDateTime

class DecreaseCustomerSharesPipelineStep(private val repository: CustomerSharesDetailRepository): PipelineStep {

    override fun execute(input: Any): Any {
        val customerOrder = input as CustomerTradingOrder

        val soldShares = customerOrder.orderDetail.toShares()

        val newSharesBalance = customerOrder.sharesDetail
            ?.let { it - soldShares }
                ?: soldShares

        this.repository.update(customerOrder.customer.customerId, newSharesBalance)

        return customerOrder.copy(
            sharesDetail = newSharesBalance,
            orderDetail = customerOrder.orderDetail.copy(
                status = OrderStatus.EXECUTED,
                updatedAt = LocalDateTime.now()
            )
        )
    }
}
