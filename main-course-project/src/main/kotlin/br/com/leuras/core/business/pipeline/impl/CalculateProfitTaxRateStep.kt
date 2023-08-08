package br.com.leuras.core.business.pipeline.impl

import br.com.leuras.core.business.pipeline.PipelineStep
import br.com.leuras.core.entity.CustomerTradingOrder

class CalculateProfitTaxRateStep: PipelineStep {

    companion object {
        private const val TAX_RATE = 0.20
        private const val TAX_FREE_LIMIT = 20000.0
    }

    override fun execute(input: Any): Any {
        val order = input as CustomerTradingOrder

        if (this.isTaxFree(order)) {
            val newOrderDetail = order.orderDetail.copy(tax = 0.0)
            return order.copy(orderDetail = newOrderDetail)
        }

        return this.applyTaxRate(order)
    }

    private fun isTaxFree(order: CustomerTradingOrder): Boolean {
        val negotiatedAmount = order.orderDetail.quantity * order.orderDetail.unitPrice

        return (negotiatedAmount <= TAX_FREE_LIMIT || order.profit == 0.0)
    }

    private fun applyTaxRate(order: CustomerTradingOrder): CustomerTradingOrder {
        val newOrderDetail = order.orderDetail.copy(tax = (order.profit * TAX_RATE))

        return order.copy(
            orderDetail = newOrderDetail
        )
    }
}