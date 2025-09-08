-- ENUM for component types
CREATE TYPE component_type AS ENUM (
    'CPU', 'GPU', 'RAM', 'SSD', 'HDD', 'Motherboard', 'PSU', 'Case', 'Cooler'
);

-- ENUM for cart status
CREATE TYPE cart_status AS ENUM (
    'DRAFT', 'ACTIVE', 'FINALIZED', 'CANCELLED'
);

-- Users table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    password_hash TEXT,
    budget NUMERIC(10, 2) NOT NULL CHECK (budget >= 0),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Components table with enhanced metadata field
CREATE TABLE components (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    brand VARCHAR(50) NOT NULL,                -- Manufacturer/brand name
    type VARCHAR(20) NOT NULL,                 -- Component type
    compatibility_tag VARCHAR(50) NOT NULL,    -- Primary compatibility identifier (e.g., CPU socket type)
    price NUMERIC(10, 2) NOT NULL CHECK (price >= 0),
    stock_quantity INTEGER NOT NULL DEFAULT 0 CHECK (stock_quantity >= 0),
    socket VARCHAR(20),                        -- Physical socket type for CPUs, motherboards, etc.
    ram_type VARCHAR(10),                      -- Compatible RAM standard (e.g., DDR4, DDR5)
    wattage INTEGER,                           -- Power consumption or supply wattage for PSU compatibility
    form_factor VARCHAR(20),                   -- Physical size/shape standard (e.g., ATX, Micro-ATX)
    gpu_length_mm INTEGER,                     -- Maximum GPU length supported (mm) for cases/motherboards
    cooler_height_mm INTEGER,                  -- Maximum CPU cooler height supported (mm) for cases
    psu_form_factor VARCHAR(20),               -- PSU size standard (e.g., ATX, SFX)
    pci_slots_required INTEGER,                -- Number of PCI slots the component occupies
    extra_compatibility JSONB,                 -- Flexible JSON field for additional compatibility info (e.g., chipset versions, BIOS versions)
    metadata JSONB DEFAULT '{}',               -- Enhanced metadata for performance, templates, and features
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Indexes for frequently queried fields
CREATE INDEX idx_components_type ON components(type);
CREATE INDEX idx_components_brand ON components(brand);
CREATE INDEX idx_components_compatibility_tag ON components(compatibility_tag);
CREATE INDEX idx_components_socket ON components(socket);
CREATE INDEX idx_components_metadata ON components USING GIN(metadata);

-- Build carts table
CREATE TABLE build_carts (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    name VARCHAR(100) DEFAULT 'Untitled Build',
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    total_price NUMERIC(10, 2) NOT NULL DEFAULT 0,
    finalized_at TIMESTAMP NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Cart items table
CREATE TABLE cart_items (
    id SERIAL PRIMARY KEY,
    cart_id INTEGER NOT NULL REFERENCES build_carts(id) ON DELETE CASCADE,
    component_id INTEGER NOT NULL REFERENCES components(id),
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT unique_cart_component UNIQUE (cart_id, component_id)
);