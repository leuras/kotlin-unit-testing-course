package br.com.leuras.app.infrastructure.sql

object CustomerAccountSQL {

    private const val COLUMNS = """
        customer_id,
        customer_name,
        qualified_investor,
        shares,
        orders,
        losses,
        created_at,
        updated_at
    """

    const val SELECT = """
        SELECT $COLUMNS FROM customer_account
        WHERE customer_id = ?
    """

    const val UPDATE = """
        UPDATE customer_account SET
            losses = ?,
            updated_at = ?
        WHERE customer_id = ?
    """
}