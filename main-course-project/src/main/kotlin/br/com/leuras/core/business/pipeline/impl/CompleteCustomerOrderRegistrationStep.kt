package br.com.leuras.core.business.pipeline.impl

import br.com.leuras.core.business.pipeline.PipelineStep
import br.com.leuras.core.entity.CustomerTradingOrder
import br.com.leuras.core.enums.OrderStatus
import java.time.LocalDateTime

class CompleteCustomerOrderRegistrationStep: PipelineStep {

    override fun execute(input: Any): Any {
        val order = input as CustomerTradingOrder

        return order.takeIf { it.orderDetail.status == OrderStatus.PENDING }
            ?.let {
                it.copy(
                    orderDetail = it.orderDetail.copy(
                        status = OrderStatus.REGISTERED,
                        updatedAt = LocalDateTime.now()
                    )
                )
            } ?: order
    }
}