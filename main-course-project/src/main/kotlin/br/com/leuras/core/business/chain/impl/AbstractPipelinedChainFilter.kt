package br.com.leuras.core.business.chain.impl

import br.com.leuras.core.business.chain.ChainFilter
import br.com.leuras.core.business.pipeline.Pipeline

abstract class AbstractPipelinedChainFilter<T, K>(
    protected val pipeline: Pipeline): ChainFilter<T, K> {

    var next: ChainFilter<T, K>? = null

    override fun next(next: ChainFilter<T, K>) {
        this.next = next
    }
}