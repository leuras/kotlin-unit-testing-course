package br.com.leuras.core.business.chain.impl

import br.com.leuras.core.business.pipeline.Pipeline
import br.com.leuras.core.entity.CustomerTradingOrder
import br.com.leuras.core.enums.OrderAction
import br.com.leuras.core.enums.OrderOperation

class ExecuteBuyTradingOrderChainFilter(pipeline: Pipeline):
    AbstractPipelinedChainFilter<CustomerTradingOrder, CustomerTradingOrder>(pipeline) {

    override fun process(input: CustomerTradingOrder, args: Map<String, Any>): CustomerTradingOrder? {
        val action = args[OrderAction.KEY] as OrderAction?

        if (OrderAction.EXECUTION == action && input.orderDetail.operation == OrderOperation.BUY) {
            return this.pipeline.run(input) as CustomerTradingOrder?
        }

        return this.next?.process(input, args)
    }
}