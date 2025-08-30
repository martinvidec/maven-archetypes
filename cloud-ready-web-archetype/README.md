# Cloud-Ready Web Application Maven Archetype

This Maven archetype generates a production-ready Spring Boot web application with comprehensive cloud-native features.

## Features

The generated project includes:

- **Spring Boot 3.2.0** with Java 17
- **Spring Security** with JWT authentication
- **Spring Data JPA** with PostgreSQL database
- **Redis** for caching
- **OpenAPI/Swagger** documentation
- **Testcontainers** for integration testing
- **Docker** and **Docker Compose** configuration
- **GitHub Actions** CI/CD pipeline
- **Health checks** and monitoring
- **Code quality** tools (Checkstyle, SpotBugs)
- **Security scanning** (OWASP dependency check)

## Usage

### Generate a new project

```bash
mvn archetype:generate \
    -DgroupId=com.yourcompany \
    -DartifactId=your-app-name \
    -Dversion=1.0.0-SNAPSHOT \
    -Dpackage=com.yourcompany.yourapp \
    -DarchetypeGroupId=com.github.martinvidec \
    -DarchetypeArtifactId=cloud-ready-web-archetype \
    -DarchetypeVersion=1.0.1 \
    -DinteractiveMode=false
```

### Parameters

- `groupId`: Your organization's group ID (e.g., `com.yourcompany`)
- `artifactId`: Your application name (e.g., `my-web-app`)
- `version`: Initial version (e.g., `1.0.0-SNAPSHOT`)
- `package`: Root package name (e.g., `com.yourcompany.mywebapp`)

## Generated Project Structure

```
your-app-name/
├── src/main/java/
│   └── com/yourcompany/yourapp/
│       ├── Application.java              # Main Spring Boot application
│       ├── config/                       # Configuration classes
│       ├── controller/                   # REST controllers
│       ├── domain/                       # JPA entities
│       ├── dto/                         # Data transfer objects
│       ├── exception/                    # Exception handling
│       ├── repository/                   # JPA repositories
│       ├── security/                     # Security configuration
│       └── service/                      # Business logic
├── src/main/resources/
│   ├── application.yml                   # Application configuration
│   ├── application-dev.yml              # Development profile
│   ├── application-test.yml             # Test profile
│   └── db/migration/                    # Flyway database migrations
├── src/test/java/                       # Test classes
├── Dockerfile                           # Docker image configuration
├── docker-compose.yml                  # Local development environment
├── .github/workflows/ci.yml            # GitHub Actions CI/CD
└── pom.xml                             # Maven configuration
```

## Quick Start

After generating your project:

1. **Start local dependencies:**
   ```bash
   cd your-app-name
   docker-compose up -d
   ```

2. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

3. **Access the application:**
   - Application: http://localhost:8080
   - API Documentation: http://localhost:8080/swagger-ui.html
   - Health Check: http://localhost:8080/actuator/health

## Development

### Running Tests

```bash
# Unit tests
mvn test

# Integration tests
mvn verify

# With test coverage
mvn clean verify jacoco:report
```

### Code Quality

```bash
# Run code quality checks
mvn clean verify spotbugs:check checkstyle:check

# OWASP dependency vulnerability scan
mvn dependency-check:check
```

### Building

```bash
# Build JAR
mvn clean package

# Build Docker image
mvn clean package jib:build
```

## Configuration

The generated application supports multiple configuration profiles:

- **default**: Production configuration
- **dev**: Development configuration with H2 database
- **test**: Test configuration with in-memory database

Configure your application by editing `src/main/resources/application.yml` and profile-specific files.

## Database

The application is configured to use:
- **PostgreSQL** for production/development
- **H2** for testing

Database migrations are managed by Flyway in `src/main/resources/db/migration/`.

## Security

The application includes:
- JWT-based authentication
- Role-based authorization
- Security headers configuration
- CORS configuration

## Monitoring

Built-in monitoring and observability:
- Spring Boot Actuator endpoints
- Prometheus metrics
- Distributed tracing with Micrometer
- Health checks for dependencies

## Deployment

The generated project includes:
- **Dockerfile** for containerization
- **docker-compose.yml** for local development
- **GitHub Actions** for CI/CD
- **Kubernetes** manifests (optional)

## Testing

The archetype includes comprehensive testing setup:
- Unit tests with JUnit 5 and Mockito
- Integration tests with Spring Boot Test
- Database testing with Testcontainers
- Security testing with Spring Security Test

## Architecture

The generated application follows best practices:
- Layered architecture (Controller → Service → Repository → Domain)
- Dependency injection with Spring
- Configuration management
- Error handling with global exception handlers
- API documentation with OpenAPI/Swagger

## Support

For issues and questions about the archetype, please visit the [GitHub repository](https://github.com/martinvidec/maven-archetypes).