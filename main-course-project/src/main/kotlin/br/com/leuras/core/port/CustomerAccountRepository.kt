package br.com.leuras.core.port

import br.com.leuras.core.entity.CustomerAccount

interface CustomerAccountRepository {
    fun find(customerId: String): CustomerAccount?
}