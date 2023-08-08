CREATE TABLE customer_account (
    customer_id VARCHAR NOT NULL PRIMARY KEY,
    customer_name VARCHAR NOT NULL,
    qualified_investor BOOLEAN NOT NULL DEFAULT 'f',
    shares JSONB NULL DEFAULT NULL,
    orders JSONB NULL DEFAULT NULL,
    losses NUMERIC(5,2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

INSERT INTO customer_account (customer_id, customer_name, qualified_investor, losses) VALUES
    (gen_random_uuid(), 'Trevor Philips', 'f',0.0),
    (gen_random_uuid(), 'Lara Croft', 't',  0.0),
    (gen_random_uuid(), 'Terry Bogard', 'f', 0.0),
    (gen_random_uuid(), 'Franklin Clinton', 'f', 0.0),
    (gen_random_uuid(), 'Michael De Santa', 't', 0.0);


CREATE OR REPLACE VIEW public.vw_customer_orders
AS SELECT customer_account.customer_id,
    customer_account.customer_name,
    customer_account.qualified_investor,
    orders."orderId",
    orders."tickerSymbol",
    orders."operation",
    orders."quantity",
    orders."unitPrice",
    orders."tax",
    orders."brokerageFee",
    orders."status",
    orders."createdAt",
    orders."updatedAt"
   FROM customer_account
     CROSS JOIN LATERAL json_to_recordset(customer_account.orders::json) orders(
        "orderId" character varying,
        "tickerSymbol" character varying,
        "operation" character varying,
        "quantity" integer,
        "unitPrice" numeric(5,2),
        "tax" character varying,
        "brokerageFee" numeric(5,2),
        "status" character varying,
        "createdAt" timestamp without time zone,
        "updatedAt" timestamp without time zone
     );

CREATE OR REPLACE VIEW public.vw_customer_shares
AS SELECT customer_account.customer_id,
    customer_account.customer_name,
    customer_account.qualified_investor,
    shares."tickerSymbol",
    shares."shares",
    shares."averagePrice",
    shares."createdAt",
    shares."updatedAt"
   FROM customer_account
     CROSS JOIN LATERAL json_to_recordset(customer_account.shares::json) shares(
        "tickerSymbol" character varying,
        "shares" integer,
        "averagePrice" numeric(5,2),
        "createdAt" timestamp without time zone,
        "updatedAt" timestamp without time zone
     );