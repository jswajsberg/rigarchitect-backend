-- RigArchitect Database Setup Script
-- Complete setup for development environment

-- Create database (run this as postgres superuser)
-- CREATE DATABASE rigarchitect;
-- \c rigarchitect;

-- Run schema creation
\i schema.sql

-- Insert dummy data
\i dummy-data.sql

-- Verify setup
SELECT 'Database setup complete!' as status;
SELECT 'Tables created:' as info;
SELECT schemaname, tablename FROM pg_tables WHERE schemaname = 'public';

SELECT 'Sample data counts:' as info;
SELECT 'Users: ' || COUNT(*) FROM users;
SELECT 'Components: ' || COUNT(*) FROM components;
SELECT 'Build Carts: ' || COUNT(*) FROM build_carts;
SELECT 'Cart Items: ' || COUNT(*) FROM cart_items;