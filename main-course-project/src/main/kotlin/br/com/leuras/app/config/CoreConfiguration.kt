package br.com.leuras.app.config

import br.com.leuras.core.business.chain.ChainFilterOrchestrator
import br.com.leuras.core.business.chain.impl.RegisterCustomerTradingOrderChainFilter
import br.com.leuras.core.business.pipeline.Pipeline
import br.com.leuras.core.business.pipeline.impl.RegistrationRulesPipelineStep
import br.com.leuras.core.port.CustomerAccountRepository
import br.com.leuras.core.port.CustomerOrderRepository
import br.com.leuras.core.usecase.OrderManagementUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CoreConfiguration {

    @Autowired
    private lateinit var orderRepository: CustomerOrderRepository

    @Autowired
    private lateinit var accountRepository: CustomerAccountRepository

    @Bean
    fun orderDispatcherUseCase(): OrderManagementUseCase {
        val registerOrderPipeline = Pipeline()
            .addStep(RegistrationRulesPipelineStep())

        val rootChainFilter = ChainFilterOrchestrator.create(
            RegisterCustomerTradingOrderChainFilter(registerOrderPipeline)
        )

        return OrderManagementUseCase(
            orderRepository,
            accountRepository,
            rootChainFilter
        )
    }
}