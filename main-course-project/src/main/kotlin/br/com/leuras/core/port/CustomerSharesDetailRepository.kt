package br.com.leuras.core.port

import br.com.leuras.core.entity.SharesDetail

interface CustomerSharesDetailRepository {
    fun update(customerId: String, shares: SharesDetail)
}