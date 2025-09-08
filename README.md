# RigArchitect Backend

A comprehensive Spring Boot application for PC build management and component selection. RigArchitect helps users build custom PC configurations, manage build carts, and track budgets.

## 🚀 Features

### Core Functionality
- **User Management**: Registration, authentication, and profile management
- **Component Catalog**: Browse and search PC components with advanced filtering
- **Build Cart System**: Create and manage multiple PC build configurations
- **Budget Tracking**: Monitor spending and budget limits for builds
- **JWT Authentication**: Secure API access with refresh token support

### API Features
- **RESTful API**: Clean REST endpoints for all operations
- **OpenAPI Documentation**: Interactive Swagger UI for API exploration
- **Pagination Support**: Efficient handling of large datasets
- **Advanced Filtering**: Component search with multiple criteria
- **Input Validation**: Comprehensive request validation

## 🛠 Tech Stack

- **Framework**: Spring Boot 3.5.5
- **Language**: Java 17
- **Database**: PostgreSQL
- **Security**: Spring Security with JWT
- **Documentation**: OpenAPI 3 (Swagger)
- **ORM**: Spring Data JPA / Hibernate
- **Build Tool**: Maven
- **Code Quality**: Lombok for boilerplate reduction

## 📋 Prerequisites

Before running the application, ensure you have:

- Java 17 or higher
- PostgreSQL database server
- Maven 3.6+ (or use the included Maven wrapper)
- Git (for cloning the repository)

## 🏗 Project Structure

```
src/main/java/com/rigarchitect/
├── config/              # Configuration classes (Security, JWT, OpenAPI)
├── controller/          # REST API controllers
├── dto/                 # Data Transfer Objects
│   ├── auth/           # Authentication DTOs
│   ├── buildcart/      # Build cart DTOs
│   ├── cartitem/       # Cart item DTOs
│   ├── component/      # Component DTOs
│   └── user/           # User DTOs
├── exception/          # Custom exception classes
├── model/              # JPA entity classes
│   └── enums/         # Enum definitions
├── repository/         # Data access layer
└── service/           # Business logic layer
```

## 🚦 Getting Started

### 1. Clone the Repository
```bash
git clone <repository-url>
cd RigArchitect
```

### 2. Database Setup

**Quick Setup (Recommended):**
```bash
# Create database
createdb rigarchitect

# Run complete setup with schema and sample data
cd database
psql -d rigarchitect -f setup.sql
```

**Manual Setup:**
```bash
# Create database
createdb rigarchitect

# Connect and run schema
psql -d rigarchitect -f database/schema.sql

# Insert sample data (optional but recommended)
psql -d rigarchitect -f database/dummy-data.sql
```

See [`database/README.md`](./database/README.md) for detailed database setup instructions and schema information.

### 3. Configure Application Properties
Update `src/main/resources/application.properties`:
```properties
spring.application.name=RigArchitect
spring.datasource.url=jdbc:postgresql://localhost:5432/rigarchitect
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=validate

# Swagger/OpenAPI Configuration
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.operationsSorter=method
springdoc.default-consumes-media-type=application/json
springdoc.default-produces-media-type=application/json
```

### 4. Run the Application
Using Maven wrapper:
```bash
./mvnw spring-boot:run
```

Or using installed Maven:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 📚 API Documentation

Once the application is running, you can access:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI Spec**: http://localhost:8080/v3/api-docs

## 🔐 Authentication

The API uses JWT-based authentication. To access protected endpoints:

1. **Register** a new user: `POST /api/v1/auth/signup`
2. **Login** to get JWT tokens: `POST /api/v1/auth/signin`
3. Include the access token in the `Authorization` header: `Bearer <your-token>`
4. **Refresh** tokens when needed: `POST /api/v1/auth/refreshtoken`

## 🛡 API Endpoints

### Authentication (`/api/v1/auth`)
- `POST /signup` - Register new user
- `POST /signin` - User login
- `POST /refreshtoken` - Refresh JWT token
- `POST /change-password` - Change user password

### Users (`/api/v1/users`)
- `GET /{id}` - Get user by ID
- `GET /email/{email}` - Get user by email
- `PUT /{id}` - Update user profile
- `PUT /{id}/budget` - Update user budget

### Components (`/api/v1/components`)
- `GET /` - List components with pagination and filtering
- `GET /{id}` - Get component by ID
- `POST /` - Create new component
- `PUT /{id}` - Update component
- `DELETE /{id}` - Delete component

### Build Carts (`/api/v1/carts`)
- `GET /user/{userId}` - Get user's build carts
- `GET /{id}` - Get a cart by ID
- `POST /user/{userId}` - Create new cart
- `PUT /{id}` - Update cart
- `POST /{cartId}/finalize` - Finalize cart
- `DELETE /{id}` - Delete cart

### Cart Items (`/api/v1/cart-items`)
- `GET /cart/{cartId}` - Get cart items
- `POST /` - Add item to cart
- `PUT /{id}` - Update cart item quantity
- `DELETE /{id}` - Remove item from cart

## 🧪 Testing

Run the test suite:
```bash
./mvnw test
```

## 🏗 Building for Production

Create a production build:
```bash
./mvnw clean package -DskipTests
```

The JAR file will be created in the `target/` directory.

## 📊 Database Schema

The application uses the following main entities:

### Core Tables
- **`users`** - User accounts with authentication details and budget tracking
- **`components`** - PC components with detailed specifications, pricing, and rich metadata
- **`build_carts`** - User's build configurations/shopping carts
- **`cart_items`** - Items within build carts (linking components to carts)

### Key Features
- **Rich Metadata**: Components include JSONB fields with performance scores, compatibility info, and template matching
- **Flexible Compatibility**: Advanced compatibility system supporting socket types, form factors, power requirements
- **Sample Data**: 300+ realistic PC components across all categories (CPUs, GPUs, RAM, storage, etc.)
- **Performance Optimized**: Comprehensive indexing including GIN indexes for JSON queries

### Database Files
- [`database/schema.sql`](./database/schema.sql) - Complete database schema
- [`database/dummy-data.sql`](./database/dummy-data.sql) - Comprehensive sample data
- [`database/setup.sql`](./database/setup.sql) - Automated setup script
- [`database/README.md`](./database/README.md) - Detailed database documentation

## ⚙️ Configuration

### JWT Configuration
Configure JWT settings in `application.properties`:
```properties
rigarchitect.app.jwtSecret=your-secret-key
rigarchitect.app.jwtExpirationMs=86400000
rigarchitect.app.jwtRefreshExpirationMs=604800000
```

### CORS Configuration
Currently configured for development with frontend at `http://localhost:5173`. Update CORS settings for production deployment.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License — see the LICENSE file for details.

## 🆘 Support

For support and questions:
- Create an issue in the GitHub repository
- Check the API documentation at `/swagger-ui.html`
- Review the application logs for debugging information

## 🔄 Version History

- **0.0.1-SNAPSHOT** - Initial development version
  - Basic user management and authentication
  - Component catalog with advanced search
  - Build cart functionality
  - JWT-based security
  - OpenAPI documentation

---

Built with ❤️ using Spring Boot and modern Java practices.