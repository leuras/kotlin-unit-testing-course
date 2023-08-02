package br.com.leuras.app.infrastructure.sql

object CustomerOrderSQL {

    const val SELECT = """
        SELECT orders FROM customer_account
        WHERE customer_id = ?
    """

    const val UPDATE = """
        UPDATE customer_account SET
            orders = ?::jsonb
        WHERE customer_id = ?
    """
}