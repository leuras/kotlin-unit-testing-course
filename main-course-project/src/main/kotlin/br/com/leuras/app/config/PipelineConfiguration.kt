package br.com.leuras.app.config

import br.com.leuras.core.business.pipeline.Pipeline
import br.com.leuras.core.business.pipeline.impl.CalculateProfitTaxRateStep
import br.com.leuras.core.business.pipeline.impl.CompleteCustomerOrderRegistrationStep
import br.com.leuras.core.business.pipeline.impl.DecreaseCustomerSharesStep
import br.com.leuras.core.business.pipeline.impl.DefineBrokerageFeeStep
import br.com.leuras.core.business.pipeline.impl.DetermineProfitOrLossStep
import br.com.leuras.core.business.pipeline.impl.IncreaseCustomerSharesStep
import br.com.leuras.core.business.pipeline.impl.PreValidateCustomerOrderStep
import br.com.leuras.core.port.CustomerAccountRepository
import br.com.leuras.core.port.CustomerOrderRepository
import br.com.leuras.core.port.CustomerSharesDetailRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PipelineConfiguration {

    @Autowired
    private lateinit var orderRepository: CustomerOrderRepository

    @Autowired
    private lateinit var accountRepository: CustomerAccountRepository

    @Autowired
    private lateinit var sharesRepository: CustomerSharesDetailRepository

    @Bean
    fun registerTradingOrderPipeline() = Pipeline()
        .addStep(PreValidateCustomerOrderStep(this.orderRepository))
        .addStep(DefineBrokerageFeeStep())
        .addStep(CompleteCustomerOrderRegistrationStep())

    @Bean
    fun executeBuyTradingOrderPipeline() = Pipeline()
        .addStep(IncreaseCustomerSharesStep(this.sharesRepository))

    @Bean
    fun executeSellTradingOrderPipeline() = Pipeline()
        .addStep(DetermineProfitOrLossStep(this.accountRepository))
        .addStep(CalculateProfitTaxRateStep())
        .addStep(DecreaseCustomerSharesStep(this.sharesRepository))
}