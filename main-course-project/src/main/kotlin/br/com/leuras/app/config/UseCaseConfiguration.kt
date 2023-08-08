package br.com.leuras.app.config

import br.com.leuras.core.business.chain.ChainFilterOrchestrator
import br.com.leuras.core.business.chain.impl.ExecuteBuyTradingOrderChainFilter
import br.com.leuras.core.business.chain.impl.ExecuteSellTradingOrderChainFilter
import br.com.leuras.core.business.chain.impl.RegisterCustomerTradingOrderChainFilter
import br.com.leuras.core.business.pipeline.Pipeline
import br.com.leuras.core.port.CustomerAccountRepository
import br.com.leuras.core.port.CustomerOrderRepository
import br.com.leuras.core.usecase.OrderManagementUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UseCaseConfiguration {

    @Autowired
    private lateinit var orderRepository: CustomerOrderRepository

    @Autowired
    private lateinit var accountRepository: CustomerAccountRepository

    @Autowired
    private lateinit var registerCustomerTradingPipeline: Pipeline

    @Autowired
    private lateinit var executeBuyTradingOrderPipeline: Pipeline

    @Autowired
    private lateinit var executeSellTradingOrderPipeline: Pipeline

    @Bean
    fun orderManagementUseCase() = OrderManagementUseCase(
            this.orderRepository,
            this.accountRepository,
            this.buildRootChainFilter()
    )

    private fun buildRootChainFilter() = ChainFilterOrchestrator.create(
            root = RegisterCustomerTradingOrderChainFilter(
                pipeline = this.registerCustomerTradingPipeline
            ),
            ExecuteBuyTradingOrderChainFilter(
                pipeline = this.executeBuyTradingOrderPipeline
            ),
            ExecuteSellTradingOrderChainFilter(
                pipeline = this.executeSellTradingOrderPipeline
            )
        )
    }
