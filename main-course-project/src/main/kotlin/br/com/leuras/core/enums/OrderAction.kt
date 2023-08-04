package br.com.leuras.core.enums

enum class OrderAction {
    REGISTRATION, EXECUTION, MODIFICATION, CANCELLATION;

    companion object {
        const val KEY = "action"
    }
}

fun OrderAction.toArgs(): Map<String, OrderAction> = mapOf(OrderAction.KEY to this)