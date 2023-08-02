package br.com.leuras.core.business.chain.impl

import br.com.leuras.core.business.pipeline.Pipeline
import br.com.leuras.core.entity.CustomerTradingOrder

class RegisterCustomerTradingOrderChainFilter(pipeline: Pipeline):
    AbstractPipelinedChainFilter<CustomerTradingOrder, CustomerTradingOrder>(pipeline) {

    override fun process(input: CustomerTradingOrder): CustomerTradingOrder? {
        return this.pipeline.run(input) as CustomerTradingOrder?
    }
}
