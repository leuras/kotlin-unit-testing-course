package br.com.leuras.core.usecase

import br.com.leuras.core.business.chain.ChainFilter
import br.com.leuras.core.entity.CustomerTradingOrder
import br.com.leuras.core.entity.TradingOrder
import br.com.leuras.core.enums.OrderAction
import br.com.leuras.core.enums.OrderStatus
import br.com.leuras.core.enums.toArgs
import br.com.leuras.core.exception.CustomerAccountNotFoundException
import br.com.leuras.core.exception.CustomerOrderNotFoundException
import br.com.leuras.core.port.CustomerAccountRepository
import br.com.leuras.core.port.CustomerOrderRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderManagementUseCase(
    private val orderRepository: CustomerOrderRepository,
    private val accountRepository: CustomerAccountRepository,
    private val rootChainFilter: ChainFilter<CustomerTradingOrder, CustomerTradingOrder>,
    private val coroutineContext: CoroutineDispatcher = Dispatchers.IO) {

    fun register(tradingOrder: TradingOrder) {
        val order = this.accountRepository.find(tradingOrder.customerId)
            ?.plus(tradingOrder) ?: throw CustomerAccountNotFoundException()

        CoroutineScope(this.coroutineContext).launch {
            val newOrder = rootChainFilter.process(
                input = order,
                args  = OrderAction.REGISTRATION.toArgs()
            ) as CustomerTradingOrder

            orderRepository.update(newOrder)
        }
    }

    fun execute(orderId: String): CustomerTradingOrder {
        val order = this.orderRepository.find(orderId)
            ?.takeIf { it.orderDetail.status == OrderStatus.REGISTERED }
                ?: throw CustomerOrderNotFoundException()

        val newOrder = this.rootChainFilter.process(
            input = order,
            args  = OrderAction.EXECUTION.toArgs()
        ) as CustomerTradingOrder

        return this.orderRepository.update(newOrder)
    }
}
