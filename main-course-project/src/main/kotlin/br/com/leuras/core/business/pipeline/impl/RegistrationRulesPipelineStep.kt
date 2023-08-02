package br.com.leuras.core.business.pipeline.impl

import br.com.leuras.core.business.pipeline.PipelineStep
import br.com.leuras.core.entity.CustomerTradingOrder
import br.com.leuras.core.enums.OrderStatus

class RegistrationRulesPipelineStep : PipelineStep {

    override fun execute(input: Any): Any {
        val customerOrder = input as CustomerTradingOrder

        return customerOrder.takeIf { it.order.status in listOf(OrderStatus.PENDING, OrderStatus.REGISTERED) }
            ?.takeUnless { it.order.tickerSymbol.trim().isEmpty() }
            ?.takeUnless { it.order.quantity <= 0 }
            ?.takeUnless { it.order.unitPrice <= 0.0 }
            ?: customerOrder.copy(
                order = customerOrder.order.copy(
                    status = OrderStatus.REJECTED
                )
            )
    }
}