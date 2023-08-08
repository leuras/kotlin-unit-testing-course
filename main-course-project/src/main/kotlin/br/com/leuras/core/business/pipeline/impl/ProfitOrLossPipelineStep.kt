package br.com.leuras.core.business.pipeline.impl

import br.com.leuras.core.business.pipeline.PipelineStep
import br.com.leuras.core.entity.CustomerAccount
import br.com.leuras.core.entity.CustomerTradingOrder
import br.com.leuras.core.exception.CustomerAccountNotFoundException
import br.com.leuras.core.port.CustomerAccountRepository
import java.time.LocalDateTime
import kotlin.math.abs

class ProfitOrLossPipelineStep(
    private val accountRepository: CustomerAccountRepository): PipelineStep {

    override fun execute(input: Any): Any {
        val order = input as CustomerTradingOrder
        val customerAccount = this.accountRepository.find(order.customer.customerId)
            ?: throw CustomerAccountNotFoundException()

        val grossAmount = this.calculateGrossAmount(order)

        if (grossAmount < 0.0) {
            this.accumulateLoss(customerAccount, loss = grossAmount)
            return order.copy(profit = 0.0)
        }

        val (netProfit, netLoss) = this.deductLosses(
            grossProfit = grossAmount,
            accumulatedLosses = customerAccount.losses
        )

        this.accountRepository.update(
            customerAccount.copy(
                losses = netLoss,
                updatedAt = LocalDateTime.now()
            )
        )

        return order.copy(profit = netProfit)
    }

    private fun calculateGrossAmount(order: CustomerTradingOrder): Double {
        val balance = order.orderDetail.quantity * (order.sharesDetail?.averagePrice ?: 0.0)
        val sold = order.orderDetail.quantity * order.orderDetail.unitPrice

        return sold - balance
    }

    private fun deductLosses(grossProfit: Double, accumulatedLosses: Double): Pair<Double, Double> {
        val netAmount = grossProfit - accumulatedLosses

        return if (netAmount > 0.0) {
            Pair(netAmount, 0.0)
        } else if (netAmount < 0.0) {
            Pair(0.0, abs(netAmount))
        } else {
            Pair(0.0, 0.0)
        }
    }

    private fun accumulateLoss(customerAccount: CustomerAccount, loss: Double) {
        val newBalance = customerAccount.losses + loss

        this.accountRepository.update(
            customerAccount.copy(
                losses = newBalance,
                updatedAt = LocalDateTime.now()
            )
        )
    }
}