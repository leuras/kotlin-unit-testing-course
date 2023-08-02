package br.com.leuras.core.business.pipeline.impl

import br.com.leuras.core.business.pipeline.PipelineStep
import br.com.leuras.core.port.CustomerSharesDetailRepository

class IncreaseSharesPipelineStep(
    private val walletRepository: CustomerSharesDetailRepository): PipelineStep {

    override fun execute(input: Any): Any {
//        val customerOrder = input as CustomerOrder
//
//        return this.walletRepository.find(customerOrder.customer.customerId)
//            ?.let { walletDetails ->
//
//                val newStockShares = walletDetails.shares.firstOrNull { it.tickerSymbol == customerOrder.tickerSymbol }
//                    ?.let { stockShares -> stockShares + customerOrder }
//                        ?: CustomerSharesDetail(
//                            tickerSymbol = customerOrder.tickerSymbol,
//                            shares = customerOrder.quantity,
//                            averagePrice = customerOrder.unitPrice, // TODO: deduction of brokerage fee
//                            customerId = customerOrder.customerId,
//                            createdAt = LocalDateTime.now(),
//                            updatedAt = LocalDateTime.now()
//                        )
//
//                this.walletRepository.update(newStockShares)
//                customerOrder.copy(status = OrderStatus.REGISTERED)
//
//            } ?: customerOrder.copy(status = OrderStatus.REJECTED)

        TODO()
    }
}
