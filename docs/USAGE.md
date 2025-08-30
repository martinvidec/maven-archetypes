# Usage Guide

This guide provides detailed instructions for using the Maven archetypes in this repository.

## Table of Contents

1. [Installation](#installation)
2. [Basic Usage](#basic-usage)
3. [Advanced Configuration](#advanced-configuration)
4. [Archetype Details](#archetype-details)
5. [Troubleshooting](#troubleshooting)
6. [Examples](#examples)

## Installation

### Using from Maven Central (When Published)

No installation required. Simply use the archetypes directly:

```bash
mvn archetype:generate \
  -DarchetypeGroupId=com.martinvidec.archetypes \
  -DarchetypeArtifactId=spring-boot-web-archetype \
  -DarchetypeVersion=1.0.0
```

### Building from Source

1. Clone the repository:
```bash
git clone https://github.com/martinvidec/maven-archetypes.git
cd maven-archetypes
```

2. Build and install to local repository:
```bash
mvn clean install
```

3. Use the locally installed archetypes:
```bash
mvn archetype:generate \
  -DarchetypeGroupId=com.martinvidec.archetypes \
  -DarchetypeArtifactId=spring-boot-web-archetype \
  -DarchetypeVersion=1.0.0-SNAPSHOT
```

## Basic Usage

### Interactive Mode

Run in interactive mode to be prompted for all parameters:

```bash
mvn archetype:generate \
  -DarchetypeGroupId=com.martinvidec.archetypes \
  -DarchetypeArtifactId=spring-boot-web-archetype
```

### Non-Interactive Mode

Specify all parameters upfront:

```bash
mvn archetype:generate \
  -DarchetypeGroupId=com.martinvidec.archetypes \
  -DarchetypeArtifactId=spring-boot-web-archetype \
  -DgroupId=com.mycompany \
  -DartifactId=my-project \
  -Dversion=1.0.0-SNAPSHOT \
  -Dpackage=com.mycompany.myproject \
  -DinteractiveMode=false
```

## Advanced Configuration

### Custom Package Structure

Use dot notation for nested packages:

```bash
mvn archetype:generate \
  -DarchetypeGroupId=com.martinvidec.archetypes \
  -DarchetypeArtifactId=spring-boot-web-archetype \
  -DgroupId=com.mycompany.division \
  -DartifactId=my-service \
  -Dpackage=com.mycompany.division.services.myservice \
  -DinteractiveMode=false
```

### Version-Specific Generation

Specify a particular Spring Boot version:

```bash
mvn archetype:generate \
  -DarchetypeGroupId=com.martinvidec.archetypes \
  -DarchetypeArtifactId=spring-boot-web-archetype \
  -DgroupId=com.mycompany \
  -DartifactId=my-project \
  -Dspring-boot-version=3.1.5 \
  -DinteractiveMode=false
```

### Custom Output Directory

Generate project in a specific directory:

```bash
mvn archetype:generate \
  -DarchetypeGroupId=com.martinvidec.archetypes \
  -DarchetypeArtifactId=spring-boot-web-archetype \
  -DgroupId=com.mycompany \
  -DartifactId=my-project \
  -DoutputDirectory=/path/to/projects \
  -DinteractiveMode=false
```

## Archetype Details

### Spring Boot Web Archetype

**Generated Structure:**
```
my-web-app/
├── pom.xml
├── README.md
├── src/
│   ├── main/
│   │   ├── java/com/example/webapp/
│   │   │   ├── Application.java
│   │   │   ├── controller/HealthController.java
│   │   │   ├── service/GreetingService.java
│   │   │   └── config/WebConfig.java
│   │   └── resources/
│   │       ├── application.yml
│   │       └── static/index.html
│   └── test/
│       └── java/com/example/webapp/
│           ├── ApplicationTests.java
│           └── controller/HealthControllerTest.java
```

**Key Features:**
- REST API endpoints (`/api/health`, `/api/greeting`)
- Spring Boot Actuator integration
- CORS configuration
- Comprehensive test coverage
- Docker support (when implemented)

**Testing the Generated App:**
```bash
cd my-web-app
mvn spring-boot:run

# Test endpoints
curl http://localhost:8080/api/health
curl http://localhost:8080/api/greeting?name=World
```

### Spring Boot JPA Archetype

**Generated Structure:**
```
my-jpa-app/
├── pom.xml
├── README.md
├── src/
│   ├── main/
│   │   ├── java/com/example/jpaapp/
│   │   │   ├── Application.java
│   │   │   ├── controller/UserController.java
│   │   │   ├── service/UserService.java
│   │   │   ├── repository/UserRepository.java
│   │   │   ├── entity/User.java
│   │   │   └── config/DatabaseConfig.java
│   │   └── resources/
│   │       ├── application.yml
│   │       └── db/migration/V1__Create_user_table.sql
│   └── test/
│       └── java/com/example/jpaapp/
│           ├── ApplicationTests.java
│           ├── repository/UserRepositoryTest.java
│           └── service/UserServiceTest.java
```

**Key Features:**
- JPA/Hibernate configuration
- Sample User entity with validation
- Repository pattern implementation
- Flyway database migrations
- H2 (development) and PostgreSQL (production) support
- Testcontainers for integration testing

**Database Configuration:**
- **Development**: H2 in-memory database
- **Production**: PostgreSQL (configure in application-prod.yml)

### Java Console Archetype

**Generated Structure:**
```
my-console-app/
├── pom.xml
├── README.md
├── src/
│   ├── main/
│   │   ├── java/com/example/console/
│   │   │   ├── Application.java
│   │   │   ├── command/CommandProcessor.java
│   │   │   └── util/InputValidator.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/example/console/
│           ├── ApplicationTest.java
│           └── command/CommandProcessorTest.java
```

**Key Features:**
- Command-line argument processing
- Input validation utilities
- Logging configuration
- Exit code handling

**Running the Generated App:**
```bash
cd my-console-app
mvn compile exec:java -Dexec.mainClass="com.example.console.Application" -Dexec.args="arg1 arg2"
```

### Java Library Archetype

**Generated Structure:**
```
my-library/
├── pom.xml
├── README.md
├── LICENSE
├── src/
│   ├── main/
│   │   ├── java/com/example/library/
│   │   │   ├── MyLibrary.java
│   │   │   └── util/Helper.java
│   │   └── resources/
│   └── test/
│       └── java/com/example/library/
│           └── MyLibraryTest.java
```

**Key Features:**
- Maven configuration for library publishing
- Javadoc generation
- Source and javadoc JAR creation
- License template
- Publishing to Maven Central preparation

**Building and Publishing:**
```bash
cd my-library
mvn clean compile test
mvn javadoc:javadoc  # Generate documentation
mvn source:jar       # Create source JAR
```

## Troubleshooting

### Common Issues

#### 1. Archetype Not Found

**Error:** `The desired archetype does not exist`

**Solution:**
- Ensure you've built the archetypes locally: `mvn clean install`
- Check the archetype coordinates (groupId, artifactId, version)
- Verify Maven can access your local repository

#### 2. Package Name Issues

**Error:** Invalid package name or compilation errors

**Solution:**
- Use valid Java package naming conventions (lowercase, dots for separation)
- Avoid Java reserved keywords
- Example: `com.mycompany.myproject` ✅, `Com.MyCompany.123Project` ❌

#### 3. Java Version Compatibility

**Error:** Unsupported class file major version

**Solution:**
- Ensure you're using Java 17 or higher
- Check `JAVA_HOME` environment variable
- Verify Maven is using the correct Java version: `mvn -version`

#### 4. Port Already in Use (Spring Boot)

**Error:** `Port 8080 was already in use`

**Solution:**
- Change the port in `application.yml`:
  ```yaml
  server:
    port: 8081
  ```
- Or stop the process using port 8080

### Debug Mode

Run Maven in debug mode for detailed output:

```bash
mvn archetype:generate -X \
  -DarchetypeGroupId=com.martinvidec.archetypes \
  -DarchetypeArtifactId=spring-boot-web-archetype
```

### Cleanup

Remove a failed generation:

```bash
rm -rf my-project  # Remove generated directory
```

## Examples

### E-commerce Microservice

```bash
mvn archetype:generate \
  -DarchetypeGroupId=com.martinvidec.archetypes \
  -DarchetypeArtifactId=spring-boot-jpa-archetype \
  -DgroupId=com.ecommerce \
  -DartifactId=product-service \
  -Dpackage=com.ecommerce.products \
  -DinteractiveMode=false

cd product-service
# Customize entities, add Product entity, configure PostgreSQL
```

### CLI Tool

```bash
mvn archetype:generate \
  -DarchetypeGroupId=com.martinvidec.archetypes \
  -DarchetypeArtifactId=java-console-archetype \
  -DgroupId=com.mycompany.tools \
  -DartifactId=file-processor \
  -Dpackage=com.mycompany.tools.fileprocessor \
  -DinteractiveMode=false

cd file-processor
# Add file processing logic, argument parsing
```

### Utility Library

```bash
mvn archetype:generate \
  -DarchetypeGroupId=com.martinvidec.archetypes \
  -DarchetypeArtifactId=java-library-archetype \
  -DgroupId=com.mycompany.utils \
  -DartifactId=string-utils \
  -Dpackage=com.mycompany.utils.string \
  -DinteractiveMode=false

cd string-utils
# Implement string utility functions, prepare for publishing
```

### API Gateway

```bash
mvn archetype:generate \
  -DarchetypeGroupId=com.martinvidec.archetypes \
  -DarchetypeArtifactId=spring-boot-web-archetype \
  -DgroupId=com.microservices \
  -DartifactId=api-gateway \
  -Dpackage=com.microservices.gateway \
  -Dspring-boot-version=3.2.0 \
  -DinteractiveMode=false

cd api-gateway
# Add Spring Cloud Gateway dependencies, configure routing
```

## Next Steps

After generating your project:

1. **Read the generated README.md** in your project
2. **Customize the configuration** files for your needs
3. **Add your business logic** to the template code
4. **Configure CI/CD** pipelines as needed
5. **Set up monitoring and logging** for production

## Getting Help

- 📖 Check the [main documentation](../README.md)
- 🔧 Review the [technical documentation](../TECHNICAL_DOCUMENTATION.md)
- 🐛 Report issues on [GitHub Issues](https://github.com/martinvidec/maven-archetypes/issues)
- 💬 Ask questions in [GitHub Discussions](https://github.com/martinvidec/maven-archetypes/discussions)