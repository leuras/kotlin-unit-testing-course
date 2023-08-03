package br.com.leuras.core.business.pipeline.impl

import br.com.leuras.core.business.pipeline.PipelineStep
import br.com.leuras.core.entity.CustomerTradingOrder
import br.com.leuras.core.enums.BrokerageFee
import java.time.LocalDateTime

class CustomerOrderBrokerageFeeStep: PipelineStep {

    override fun execute(input: Any): Any {
        val customerOrder = input as CustomerTradingOrder

        val newOrder = with(customerOrder) {
            orderDetail.copy(
                brokerageFee = if (customer.qualifiedInvestor) BrokerageFee.FREE.fee else BrokerageFee.REGULAR.fee,
                updatedAt = LocalDateTime.now()
            )
        }

        return customerOrder.copy(orderDetail = newOrder)
    }
}