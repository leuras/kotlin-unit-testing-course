package br.com.leuras.core.business.chain

object ChainFilterOrchestrator {

    inline fun <reified T, K> create(root: ChainFilter<T, K>, vararg nodes: ChainFilter<T, K>): ChainFilter<T, K> {
        var head = root

        for (node in nodes) {
            head.next(node)
            head = node
        }

        return root
    }
}