package br.com.leuras.app.config

import br.com.leuras.core.business.chain.ChainFilterOrchestrator
import br.com.leuras.core.business.chain.impl.ExecuteBuyTradingOrderChainFilter
import br.com.leuras.core.business.chain.impl.ExecuteSellTradingOrderChainFilter
import br.com.leuras.core.business.chain.impl.RegisterCustomerTradingOrderChainFilter
import br.com.leuras.core.business.pipeline.Pipeline
import br.com.leuras.core.business.pipeline.impl.CustomerOrderBrokerageFeeStep
import br.com.leuras.core.business.pipeline.impl.CustomerOrderPreValidationStep
import br.com.leuras.core.business.pipeline.impl.CustomerOrderRegistrationStatusStep
import br.com.leuras.core.business.pipeline.impl.DecreaseCustomerSharesPipelineStep
import br.com.leuras.core.business.pipeline.impl.IncreaseCustomerSharesPipelineStep
import br.com.leuras.core.business.pipeline.impl.ProfitOrLossPipelineStep
import br.com.leuras.core.business.pipeline.impl.ProfitTaxPipelineStep
import br.com.leuras.core.port.CustomerAccountRepository
import br.com.leuras.core.port.CustomerOrderRepository
import br.com.leuras.core.port.CustomerSharesDetailRepository
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

    @Autowired
    private lateinit var sharesRepository: CustomerSharesDetailRepository

    @Bean
    fun orderManagementUseCase() = OrderManagementUseCase(
            this.orderRepository,
            this.accountRepository,
            this.buildRootChainFilter()
    )

    private fun buildRootChainFilter() = ChainFilterOrchestrator.create(
            root = RegisterCustomerTradingOrderChainFilter(
                pipeline = Pipeline()
                    .addStep(CustomerOrderPreValidationStep(this.orderRepository))
                    .addStep(CustomerOrderBrokerageFeeStep())
                    .addStep(CustomerOrderRegistrationStatusStep())
            ),
            ExecuteBuyTradingOrderChainFilter(
                pipeline = Pipeline()
                    .addStep(IncreaseCustomerSharesPipelineStep(this.sharesRepository))
            ),
            ExecuteSellTradingOrderChainFilter(
                pipeline = Pipeline()
                    .addStep(ProfitOrLossPipelineStep(this.accountRepository))
                    .addStep(ProfitTaxPipelineStep())
                    .addStep(DecreaseCustomerSharesPipelineStep(this.sharesRepository))
            )
        )
}
