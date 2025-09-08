# RigArchitect Database Setup

This directory contains all the SQL files needed to set up the RigArchitect PostgreSQL database with sample data.

## Files Overview

- **`schema.sql`** - Complete database schema with tables, indexes, and constraints
- **`dummy-data.sql`** - Comprehensive sample data with realistic PC components
- **`dbschema.sql`** - Original tested schema (kept for reference)
- **`setup.sql`** - Combined setup script that runs everything in order

## Quick Setup

### Option 1: Automated Setup (Recommended)
```bash
# Create database
createdb rigarchitect

# Run complete setup
psql -d rigarchitect -f setup.sql
```

### Option 2: Manual Setup
```bash
# Create database
createdb rigarchitect

# Connect to database
psql -d rigarchitect

# Run schema creation
\i schema.sql

# Insert sample data
\i dummy-data.sql
```

### Option 3: Using pgAdmin or GUI tools
1. Create a new database named `rigarchitect`
2. Run `schema.sql` to create tables and indexes
3. Run `dummy-data.sql` to insert sample data

## Database Schema

### Tables
- **`users`** - User accounts with authentication and budget tracking
- **`components`** - PC components with detailed specifications and metadata
- **`build_carts`** - User build configurations/carts
- **`cart_items`** - Items within build carts (many-to-many relationship)

### Key Features
- **JSONB fields** for flexible metadata storage (components have rich JSON metadata)
- **Comprehensive indexing** for fast queries on common search patterns
- **Foreign key constraints** with cascade delete for data integrity
- **Check constraints** for data validation (positive prices, quantities, etc.)
- **Enum types** for component types and build statuses

## Sample Data

The dummy data includes:
- **300+ realistic PC components** across all categories:
  - CPUs (AMD Ryzen, Intel Core)
  - GPUs (NVIDIA RTX, AMD RX)
  - RAM (DDR4, DDR5 kits)
  - Storage (NVMe SSDs, SATA SSDs, HDDs)
  - Motherboards (AM4, AM5, LGA1700)
  - Power Supplies (various wattages and efficiency ratings)
  - Cases (ATX, Micro-ATX, Mini-ITX)
  - CPU Coolers (air and liquid cooling)

- **Rich metadata** for each component including:
  - Performance scores
  - Template compatibility (budget, mid-range, high-end)
  - Use cases (gaming, content creation, etc.)
  - Technical specifications
  - Compatibility information

## Configuration

Update your `application.properties` to match your database setup:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/rigarchitect
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=validate
```

## Environment-Specific Notes

### Development
- Use `spring.jpa.hibernate.ddl-auto=validate` to ensure schema matches
- Enable SQL logging: `spring.jpa.show-sql=true`

### Production
- Always use `validate` or `none` for `ddl-auto`
- Consider using database migration tools like Flyway or Liquibase
- Set up proper backup and monitoring

## Troubleshooting

**Connection Issues:**
- Ensure PostgreSQL is running
- Check database name, username, and password
- Verify host and port settings

**Schema Issues:**
- Drop and recreate database if needed: `DROP DATABASE rigarchitect; CREATE DATABASE rigarchitect;`
- Ensure your PostgreSQL version supports JSONB (9.4+)

**Data Issues:**
- Sample data is designed for development/testing
- Customize or replace with production data as needed

## Maintenance

**Updating Schema:**
1. Create migration scripts for schema changes
2. Test migrations on a copy of production data
3. Use proper database versioning

**Performance:**
- Monitor query performance with `EXPLAIN ANALYZE`
- Consider additional indexes based on usage patterns
- Regular `VACUUM` and `ANALYZE` for optimal performance