package br.com.leuras.core.usecase

import br.com.leuras.core.business.chain.ChainFilter
import br.com.leuras.core.entity.CustomerTradingOrder
import br.com.leuras.core.entity.TradingOrder
import br.com.leuras.core.exception.CustomerAccountNotFoundException
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
        val customerOrder = this.accountRepository.find(tradingOrder.customerId)
            ?.plus(tradingOrder) ?: throw CustomerAccountNotFoundException()

        CoroutineScope(this.coroutineContext).launch {
            val newCustomerOrder = rootChainFilter.process(customerOrder) as CustomerTradingOrder
            orderRepository.update(newCustomerOrder)
        }
    }
}