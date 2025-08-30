# ${artifactId}

${description}

## Overview

This is a cloud-ready Spring Boot web application generated from the cloud-ready-web-archetype. It includes comprehensive features for building production-ready applications.

## Features

- **Spring Boot 3.2.0** with Java 17
- **JWT-based Security** with OAuth2 Resource Server
- **PostgreSQL** database with Flyway migrations
- **Redis** caching and session storage
- **OpenAPI 3.0** documentation (Swagger UI)
- **Monitoring** with Micrometer and Prometheus
- **Docker** containerization
- **Multi-profile** configuration (dev, prod, test)
- **Comprehensive testing** with TestContainers
- **Code quality** tools (Checkstyle, SpotBugs, JaCoCo)

## Quick Start

### Prerequisites

- Java 17 or later
- Maven 3.9+
- Docker and Docker Compose
- PostgreSQL 15+ (or use Docker Compose)
- Redis 7+ (or use Docker Compose)

### Local Development

1. **Clone and navigate to the project:**
   ```bash
   cd ${artifactId}
   ```

2. **Start dependencies with Docker Compose:**
   ```bash
   docker-compose up postgres redis -d
   ```

3. **Run the application:**
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Access the application:**
   - API Base URL: http://localhost:8080/api
   - Swagger UI: http://localhost:8080/swagger-ui/index.html
   - Actuator Health: http://localhost:8080/actuator/health

### Using Docker

1. **Build and run with Docker Compose:**
   ```bash
   docker-compose up --build
   ```

2. **Access the application:**
   - Application: http://localhost:8080
   - Grafana: http://localhost:3000 (admin/admin)
   - Prometheus: http://localhost:9090

## Configuration

### Application Profiles

- **dev**: Development configuration with debug logging
- **prod**: Production configuration with security hardening
- **test**: Test configuration with H2 in-memory database

### Environment Variables

Key environment variables for production deployment:

- `DATABASE_URL`: PostgreSQL connection URL
- `DATABASE_USERNAME`: Database username
- `DATABASE_PASSWORD`: Database password
- `REDIS_HOST`: Redis host
- `REDIS_PORT`: Redis port
- `REDIS_PASSWORD`: Redis password

## API Documentation

The API is documented using OpenAPI 3.0 (Swagger). Access the interactive documentation at:
http://localhost:8080/swagger-ui/index.html

### Authentication

The application uses JWT-based authentication. Configure your JWT issuer in:
```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: your-jwt-issuer-uri
```

## Database

### Migrations

Database schema is managed using Flyway. Migration files are located in:
```
src/main/resources/db/migration/
```

### Schema

The application includes:
- **users**: User accounts with roles
- **user_roles**: User role assignments

## Testing

### Run Tests

```bash
# Unit tests
./mvnw test

# Integration tests
./mvnw verify

# With coverage report
./mvnw clean verify jacoco:report
```

### Test Database

Tests use H2 in-memory database and TestContainers for integration tests.

## Monitoring

### Metrics

The application exposes metrics at `/actuator/metrics` and Prometheus format at `/actuator/prometheus`.

### Health Checks

Health check endpoint: `/actuator/health`

Available health indicators:
- Database connectivity
- Redis connectivity
- Disk space
- Custom application health

## Security

### Features

- JWT-based stateless authentication
- Role-based access control (RBAC)
- CORS configuration
- Security headers (HSTS, CSP, etc.)
- Input validation
- SQL injection prevention

### Default Roles

- **ADMIN**: Full system access
- **USER**: Standard user access
- **MODERATOR**: Moderation capabilities

## Development

### Code Quality

```bash
# Run code quality checks
./mvnw checkstyle:check spotbugs:check

# Security vulnerability check
./mvnw dependency-check:check
```

### Build

```bash
# Build application
./mvnw clean package

# Build Docker image
./mvnw clean package jib:dockerBuild
```

## Deployment

### Docker

```bash
# Build and push image
docker build -t ${artifactId}:latest .
docker push your-registry/${artifactId}:latest
```

### Kubernetes

Example deployment files can be generated or added to `k8s/` directory.

### Environment Configuration

For production deployment, ensure:

1. Database connection is properly configured
2. Redis is available and configured
3. JWT issuer is properly set
4. Security configurations are reviewed
5. Monitoring is set up

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Run quality checks
6. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support and questions:
- Check the application logs
- Review the configuration
- Consult the API documentation
- Check the health endpoints