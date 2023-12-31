package br.com.leuras.core.business.pipeline.impl

import br.com.leuras.core.business.pipeline.PipelineStep
import br.com.leuras.core.entity.CustomerTradingOrder
import br.com.leuras.core.enums.OrderOperation
import br.com.leuras.core.enums.OrderStatus
import br.com.leuras.core.port.CustomerOrderRepository
import java.time.LocalDateTime

class PreValidateCustomerOrderStep(
    private val repository: CustomerOrderRepository) : PipelineStep {

    companion object {
        private const val SHARES_BLOCK_SIZE = 100
    }

    override fun execute(input: Any): Any {
        val order = input as CustomerTradingOrder

        return order.takeIf { it.orderDetail.status == OrderStatus.PENDING }
            ?.takeIf { it.orderDetail.tickerSymbol.trim().isNotBlank() }
            ?.takeIf { it.orderDetail.quantity >= SHARES_BLOCK_SIZE }
            ?.takeIf { (it.orderDetail.quantity % SHARES_BLOCK_SIZE) == 0 }
            ?.takeIf { it.orderDetail.unitPrice > 0.0 }
            ?.takeIf { this.hasEnoughShares(order) }
            ?.takeIf { this.isUnique(order) }
                ?: order.copy(
                    orderDetail = order.orderDetail.copy(
                        status = OrderStatus.REJECTED,
                        updatedAt = LocalDateTime.now()
                    )
                )
    }

    private fun hasEnoughShares(customerOrder: CustomerTradingOrder): Boolean {
        return when (customerOrder.orderDetail.operation) {
            OrderOperation.BUY -> true
            OrderOperation.SELL -> ((customerOrder.sharesDetail?.shares ?: 0) >= customerOrder.orderDetail.quantity)
        }
    }

    private fun isUnique(newOrder: CustomerTradingOrder): Boolean {
        return this.repository.findOwnedBy(newOrder.customer.customerId)
            .none { otherOrder ->
                otherOrder.createdAt.toLocalDate() == newOrder.orderDetail.createdAt.toLocalDate()
                    && otherOrder.tickerSymbol == newOrder.orderDetail.tickerSymbol
                    && otherOrder.status in listOf(OrderStatus.PENDING, OrderStatus.REGISTERED)
            }
    }
}
