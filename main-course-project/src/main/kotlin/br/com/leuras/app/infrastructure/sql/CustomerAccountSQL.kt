package br.com.leuras.app.infrastructure.sql

object CustomerAccountSQL {

    private const val COLUMNS = """
        customer_id,
        customer_name,
        qualified_investor,
        shares,
        orders
    """

    const val SELECT = """
        SELECT $COLUMNS FROM customer_account
        WHERE customer_id = ?
    """
}