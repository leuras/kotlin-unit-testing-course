package br.com.leuras.core.business.chain.impl

import br.com.leuras.core.business.pipeline.Pipeline
import br.com.leuras.core.entity.CustomerTradingOrder
import br.com.leuras.core.enums.OrderAction

class RegisterCustomerTradingOrderChainFilter(pipeline: Pipeline):
    AbstractPipelinedChainFilter<CustomerTradingOrder, CustomerTradingOrder>(pipeline) {

    override fun process(input: CustomerTradingOrder, args: Map<String, Any>): CustomerTradingOrder? {
        return when (args[OrderAction.KEY]) {
            OrderAction.REGISTRATION -> this.pipeline.run(input) as CustomerTradingOrder?
            else -> this.next?.process(input, args)
        }
    }
}
