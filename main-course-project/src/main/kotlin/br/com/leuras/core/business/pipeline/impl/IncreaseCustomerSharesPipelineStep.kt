package br.com.leuras.core.business.pipeline.impl

import br.com.leuras.core.business.pipeline.PipelineStep
import br.com.leuras.core.entity.CustomerTradingOrder
import br.com.leuras.core.enums.OrderStatus
import br.com.leuras.core.port.CustomerSharesDetailRepository
import java.time.LocalDateTime
import org.slf4j.LoggerFactory

class IncreaseCustomerSharesPipelineStep(
    private val repository: CustomerSharesDetailRepository): PipelineStep {

    companion object {
        private val log = LoggerFactory.getLogger(IncreaseCustomerSharesPipelineStep::class.java)
    }

    override fun execute(input: Any): Any {
        val customerOrder = input as CustomerTradingOrder

        log.info("Increasing customer's shares related to order n°: ${customerOrder.orderDetail.orderId}")

        val newShares = customerOrder.orderDetail.toShares()

        val newSharesBalance = customerOrder.sharesDetail
            ?.let { it + newShares }
                ?: newShares

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
