# Maven Archetypes

A collection of modern Maven archetypes for Java development, providing ready-to-use project templates for common application patterns and frameworks.

[![Build Status](https://github.com/martinvidec/maven-archetypes/workflows/Build%20and%20Test/badge.svg)](https://github.com/martinvidec/maven-archetypes/actions)
[![Maven Central](https://img.shields.io/maven-central/v/com.martinvidec.archetypes/maven-archetypes-parent.svg)](https://search.maven.org/artifact/com.martinvidec.archetypes/maven-archetypes-parent)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Available Archetypes

### 🌐 Spring Boot Web Application
**Archetype ID**: `spring-boot-web-archetype`

Creates a Spring Boot web application with REST API capabilities, including:
- Spring Boot 3.x with Java 17+
- REST controllers with sample endpoints
- Service layer architecture
- Spring Boot Actuator for monitoring
- Comprehensive testing setup with JUnit 5 and Mockito
- Configuration examples

**Use Cases**: Microservices, REST APIs, web backends

### 🗄️ Spring Boot JPA Application
**Archetype ID**: `spring-boot-jpa-archetype`

Creates a Spring Boot application with JPA/Hibernate database integration:
- Spring Data JPA configuration
- Sample entity and repository
- Database migration with Flyway
- Support for PostgreSQL and H2
- Repository and service testing
- Testcontainers integration

**Use Cases**: Data-driven applications, CRUD services, database-backed APIs

### 🖥️ Java Console Application
**Archetype ID**: `java-console-archetype`

Creates a simple Java console application:
- Modern Java 17+ setup
- Command-line argument processing
- Input validation utilities
- Logging configuration
- Unit testing framework

**Use Cases**: CLI tools, batch processing, utilities

### 📚 Java Library
**Archetype ID**: `java-library-archetype`

Creates a Java library for distribution:
- Maven configuration for library publishing
- Javadoc generation
- Source jar creation
- Testing framework
- License and documentation templates

**Use Cases**: Reusable libraries, frameworks, shared components

## Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Generate a New Project

#### Spring Boot Web Application
```bash
mvn archetype:generate \
  -DarchetypeGroupId=com.martinvidec.archetypes \
  -DarchetypeArtifactId=spring-boot-web-archetype \
  -DgroupId=com.example \
  -DartifactId=my-web-app \
  -Dpackage=com.example.webapp \
  -DinteractiveMode=false
```

#### Spring Boot JPA Application
```bash
mvn archetype:generate \
  -DarchetypeGroupId=com.martinvidec.archetypes \
  -DarchetypeArtifactId=spring-boot-jpa-archetype \
  -DgroupId=com.example \
  -DartifactId=my-jpa-app \
  -Dpackage=com.example.jpaapp \
  -DinteractiveMode=false
```

#### Java Console Application
```bash
mvn archetype:generate \
  -DarchetypeGroupId=com.martinvidec.archetypes \
  -DarchetypeArtifactId=java-console-archetype \
  -DgroupId=com.example \
  -DartifactId=my-console-app \
  -Dpackage=com.example.console \
  -DinteractiveMode=false
```

#### Java Library
```bash
mvn archetype:generate \
  -DarchetypeGroupId=com.martinvidec.archetypes \
  -DarchetypeArtifactId=java-library-archetype \
  -DgroupId=com.example \
  -DartifactId=my-library \
  -Dpackage=com.example.library \
  -DinteractiveMode=false
```

### After Generation

1. Navigate to your new project directory:
   ```bash
   cd my-web-app
   ```

2. Build and test the project:
   ```bash
   mvn clean compile test
   ```

3. Run the application (for Spring Boot projects):
   ```bash
   mvn spring-boot:run
   ```

4. Access the application:
   - Spring Boot Web: http://localhost:8080/api/health
   - View the generated README.md for specific instructions

## Features

✅ **Modern Java**: All archetypes target Java 17+ with modern language features  
✅ **Best Practices**: Industry-standard project structure and configuration  
✅ **Testing Ready**: Pre-configured testing frameworks and sample tests  
✅ **Production Ready**: Includes monitoring, logging, and deployment configuration  
✅ **Documentation**: Comprehensive README and code documentation in generated projects  
✅ **CI/CD Ready**: GitHub Actions workflows included  
✅ **Customizable**: Flexible parameters for project customization  

## Customization Parameters

When generating a project, you can customize it using these parameters:

| Parameter | Description | Default | Required |
|-----------|-------------|---------|----------|
| `groupId` | Maven group ID | `com.example` | Yes |
| `artifactId` | Project name and Maven artifact ID | - | Yes |
| `version` | Initial project version | `1.0.0-SNAPSHOT` | No |
| `package` | Base Java package | `com.example.app` | No |
| `spring-boot-version` | Spring Boot version (for Spring Boot archetypes) | `3.2.0` | No |

## Generated Project Structure

Each archetype generates a complete, ready-to-use project with:

```
my-project/
├── pom.xml                    # Maven configuration
├── README.md                  # Project documentation
├── .gitignore                 # Git ignore rules
├── src/
│   ├── main/
│   │   ├── java/              # Source code
│   │   └── resources/         # Configuration files
│   └── test/
│       └── java/              # Test code
└── .github/                   # CI/CD workflows (optional)
    └── workflows/
```

## Development

### Building the Archetypes

Clone this repository and build all archetypes:

```bash
git clone https://github.com/martinvidec/maven-archetypes.git
cd maven-archetypes
mvn clean install
```

### Testing an Archetype

Test an archetype locally after building:

```bash
# Generate a test project
mvn archetype:generate \
  -DarchetypeGroupId=com.martinvidec.archetypes \
  -DarchetypeArtifactId=spring-boot-web-archetype \
  -DarchetypeVersion=1.0.0-SNAPSHOT \
  -DgroupId=com.test \
  -DartifactId=test-project \
  -DinteractiveMode=false

# Test the generated project
cd test-project
mvn clean compile test
```

## Documentation

- 📋 [**Concept**](concept.md) - Project concept and overview
- 🔧 [**Technical Documentation**](TECHNICAL_DOCUMENTATION.md) - Complete implementation guide
- 📖 [**Usage Guide**](docs/USAGE.md) - Detailed usage instructions
- 🤝 [**Contributing**](docs/CONTRIBUTING.md) - Contributing guidelines

## Requirements

- **Java**: 17 or higher
- **Maven**: 3.6.0 or higher
- **Operating System**: Windows, macOS, or Linux

## Versioning

This project follows [Semantic Versioning](https://semver.org/):
- **Major**: Breaking changes to archetype structure or generated projects
- **Minor**: New archetypes or significant feature additions
- **Patch**: Bug fixes and minor improvements

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Support

- 🐛 **Bug Reports**: [GitHub Issues](https://github.com/martinvidec/maven-archetypes/issues)
- 💡 **Feature Requests**: [GitHub Issues](https://github.com/martinvidec/maven-archetypes/issues)
- 📧 **Email**: [martin.videc@example.com](mailto:martin.videc@example.com)

## Contributing

We welcome contributions! Please see our [Contributing Guidelines](docs/CONTRIBUTING.md) for details on:
- How to submit bug reports and feature requests
- Development setup and testing
- Code style and conventions
- Pull request process

## Acknowledgments

- Inspired by the Maven community and archetype best practices
- Built with modern Java and Spring Boot ecosystem tools
- Thanks to all contributors who help improve these archetypes

---

**Happy coding! 🚀**

> These archetypes are designed to help you bootstrap new Java projects quickly while following modern development practices. Each generated project is production-ready and includes comprehensive documentation to get you started.
