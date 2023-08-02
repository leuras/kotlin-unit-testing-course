package br.com.leuras.app.infrastructure.sql

object CustomerSharesDetailSQL {

    const val SELECT = """
        SELECT shares FROM customer_account
        WHERE customer_id = ?
    """

    const val UPDATE = """
        UPDATE customer_account SET
            shares = ?::jsonb
        WHERE customer_id = ?
    """
}