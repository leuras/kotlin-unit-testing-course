package br.com.leuras.app.infrastructure.sql

object CustomerOrderSQL {

    const val SELECT_CUSTOMER_ORDERS_BY_ID = """
        SELECT 
            customer_id,
            customer_name,
            qualified_investor,
            shares,
            orders
        FROM customer_account
            CROSS JOIN LATERAL json_to_recordset(orders::json) AS orders("orderId" VARCHAR)
        WHERE "orderId" = ?
    """

    const val SELECT_ORDERS_BY_CUSTOMER = """
        SELECT orders FROM customer_account
        WHERE customer_id = ?
    """

    const val UPDATE = """
        UPDATE customer_account SET
            orders = ?::jsonb
        WHERE customer_id = ?
    """
}