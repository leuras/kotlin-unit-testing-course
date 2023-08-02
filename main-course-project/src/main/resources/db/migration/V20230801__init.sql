CREATE TABLE customer_account (
    customer_id VARCHAR NOT NULL PRIMARY KEY,
    customer_name VARCHAR NOT NULL,
    qualified_investor BOOLEAN NOT NULL DEFAULT 'f',
    shares JSONB NULL DEFAULT NULL,
    orders JSONB NULL DEFAULT NULL
);

INSERT INTO customer_account (customer_id, customer_name, qualified_investor) VALUES
    (gen_random_uuid(), 'Trevor Philips', 'f'),
    (gen_random_uuid(), 'Lara Croft', 't'),
    (gen_random_uuid(), 'Terry Bogard', 'f'),
    (gen_random_uuid(), 'Franklin Clinton', 'f'),
    (gen_random_uuid(), 'Michael De Santa', 't');
