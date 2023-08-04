package br.com.leuras.core.business.pipeline.impl

import br.com.leuras.core.business.pipeline.PipelineStep
import br.com.leuras.core.entity.CustomerTradingOrder
import br.com.leuras.core.enums.BrokerageFee
import java.time.LocalDateTime
import org.slf4j.LoggerFactory

class CustomerOrderBrokerageFeeStep: PipelineStep {

    companion object {
        private val log = LoggerFactory.getLogger(CustomerOrderBrokerageFeeStep::class.java)
    }

    override fun execute(input: Any): Any {
        val customerOrder = input as CustomerTradingOrder

        log.info("Applying customer's order brokerage fee for order n°: ${customerOrder.orderDetail.orderId}")

        val newOrder = with(customerOrder) {
            orderDetail.copy(
                brokerageFee = if (customer.qualifiedInvestor) BrokerageFee.FREE.fee else BrokerageFee.REGULAR.fee,
                updatedAt = LocalDateTime.now()
            )
        }

        return customerOrder.copy(orderDetail = newOrder)
    }
}