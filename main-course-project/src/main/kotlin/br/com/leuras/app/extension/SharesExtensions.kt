package br.com.leuras.app.extension

import br.com.leuras.core.entity.SharesDetail

fun List<SharesDetail>.toMap() = this.map {
    mapOf(
        "tickerSymbol" to it.tickerSymbol,
        "shares" to it.shares,
        "averagePrice" to it.averagePrice,
        "createdAt" to it.createdAt,
        "updatedAt" to it.updatedAt
    )
}