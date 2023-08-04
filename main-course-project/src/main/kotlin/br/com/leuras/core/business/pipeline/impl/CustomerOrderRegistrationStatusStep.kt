package br.com.leuras.core.business.pipeline.impl

import br.com.leuras.core.business.pipeline.PipelineStep
import br.com.leuras.core.entity.CustomerTradingOrder
import br.com.leuras.core.enums.OrderStatus
import java.time.LocalDateTime
import org.slf4j.LoggerFactory

class CustomerOrderRegistrationStatusStep: PipelineStep {

    companion object {
        private val log = LoggerFactory.getLogger(CustomerOrderRegistrationStatusStep::class.java)
    }

    override fun execute(input: Any): Any {
        val customerOrder = input as CustomerTradingOrder

        log.info("Trying to change customer's order status to REGISTERED for order n°: ${customerOrder.orderDetail.orderId}")

        return customerOrder.takeIf { it.orderDetail.status == OrderStatus.PENDING }
            ?.let {
                it.copy(
                    orderDetail = it.orderDetail.copy(
                        status = OrderStatus.REGISTERED,
                        updatedAt = LocalDateTime.now()
                    )
                )
            } ?: customerOrder
    }
}