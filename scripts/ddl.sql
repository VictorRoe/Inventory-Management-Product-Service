-- Creación de la tabla de categorías (category)
CREATE TABLE category (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- Creación de la tabla de proveedores (supplier)
CREATE TABLE supplier (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NOT NULL,
    address VARCHAR(180) NOT NULL,
    company VARCHAR(50) NOT NULL
);

-- Creación de la tabla de productos con ambas claves foráneas
CREATE TABLE product (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price NUMERIC(15,2) NOT NULL,
    stock BIGINT DEFAULT 0,
    sku VARCHAR(100) UNIQUE NOT NULL,

    -- Columnas para las claves foráneas
    category_id BIGINT,
    supplier_id BIGINT, -- <-- Columna añadida para el proveedor

    CONSTRAINT fk_category
        FOREIGN KEY (category_id) REFERENCES category(id)
        ON DELETE SET NULL,

    CONSTRAINT fk_supplier -- <-- FK añadida
        FOREIGN KEY (supplier_id) REFERENCES supplier(id)
        ON DELETE SET NULL
);
