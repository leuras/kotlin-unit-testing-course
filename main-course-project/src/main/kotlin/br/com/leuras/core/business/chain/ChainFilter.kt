package br.com.leuras.core.business.chain

interface ChainFilter<T, K> {
    fun next(next: ChainFilter<T, K>)

    fun process(input: T,  args: Map<String, Any> = emptyMap()): K?
}