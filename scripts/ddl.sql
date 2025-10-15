CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price NUMERIC(15, 2) NOT NULL,
    stock BIGINT DEFAULT 0,
    sku VARCHAR(100) UNIQUE NOT NULL
);
