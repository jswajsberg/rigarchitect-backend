# RigArchitect Backend

A Spring Boot backend for building and managing custom PC configurations.

## What it does

- User authentication with JWT tokens
- Guest mode for anonymous users (sessions expire after 30 days)
- PC component catalog with search and filtering
- Shopping cart system for builds
- Budget tracking
- Guest-to-user data migration

## Tech Stack

- Spring Boot 3.5.5 / Java 17
- PostgreSQL
- Spring Security with JWT
- Spring Data JPA / Hibernate
- OpenAPI 3 / Swagger
- Maven

## Prerequisites

- Java 17+
- PostgreSQL
- Maven 3.6+ (or use `./mvnw`)

---

## Setup

### Database

Quick setup:
```bash
createdb rigarchitect
cd database
psql -d rigarchitect -f setup.sql
```

See [`database/README.md`](./database/README.md) for more details.

### Configuration

Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/rigarchitect
spring.datasource.username=your_username
spring.datasource.password=your_password

rigarchitect.app.jwtSecret=your-secret-key
rigarchitect.app.jwtExpirationMs=86400000
rigarchitect.app.jwtRefreshExpirationMs=604800000
```

### Running

```bash
./mvnw spring-boot:run
```

The app starts at `http://localhost:8080`

API docs: http://localhost:8080/swagger-ui.html

---

## Authentication

**Registered users:**
- POST `/api/v1/auth/signup` - Register
- POST `/api/v1/auth/login` - Get JWT tokens
- POST `/api/v1/auth/refresh` - Refresh token
- Include access token in requests: `Authorization: Bearer <token>`

**Guest users:**
- POST `/api/v1/guest/session` - Create session
- All component endpoints work without authentication
- Guest data can be migrated to a user account on registration

## API Endpoints

Full API documentation is available at `/swagger-ui.html` when running.

Main endpoints:
- `/api/v1/auth` - Authentication and user management
- `/api/v1/users` - User profiles and budgets
- `/api/v1/components` - Component catalog (search, filter, CRUD)
- `/api/v1/carts` - Build carts
- `/api/v1/cart-items` - Cart item management
- `/api/v1/guest` - Guest sessions and builds

## Database Schema

Main tables:
- `users` - User accounts and budgets
- `components` - PC components with specs and pricing (300+ sample components included)
- `build_carts` - Build configurations
- `cart_items` - Items in carts
- `guest_sessions` - Anonymous sessions (30-day expiration)
- `guest_builds` - Guest build data (JSONB)

See [`database/README.md`](./database/README.md) for details.

## Development

Run tests:
```bash
./mvnw test
```

Build for production:
```bash
./mvnw clean package -DskipTests
```

CORS is currently configured for `http://localhost:5173` (dev frontend). Update for production.

---

## Project Structure

```
src/main/java/com/rigarchitect/
├── config/       - Security, JWT, OpenAPI config
├── controller/   - REST controllers
├── dto/          - Request/response objects
├── exception/    - Custom exceptions
├── model/        - JPA entities
├── repository/   - Data access
└── service/      - Business logic
```