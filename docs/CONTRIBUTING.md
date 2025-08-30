# Contributing Guidelines

Thank you for your interest in contributing to the Maven Archetypes project! This document provides guidelines and information for contributors.

## Table of Contents

1. [Code of Conduct](#code-of-conduct)
2. [How to Contribute](#how-to-contribute)
3. [Development Setup](#development-setup)
4. [Creating New Archetypes](#creating-new-archetypes)
5. [Testing](#testing)
6. [Coding Standards](#coding-standards)
7. [Pull Request Process](#pull-request-process)
8. [Release Process](#release-process)

## Code of Conduct

This project adheres to a code of conduct that promotes:
- **Respectful communication** with all contributors
- **Inclusive environment** welcoming diverse perspectives
- **Constructive feedback** focused on improving the project
- **Professional behavior** in all interactions

## How to Contribute

### Bug Reports

When reporting bugs, please include:
- **Clear description** of the issue
- **Steps to reproduce** the problem
- **Expected vs actual behavior**
- **Environment details** (Java version, Maven version, OS)
- **Generated project details** (if applicable)

Use the bug report template:
```markdown
**Bug Description:**
A clear and concise description of what the bug is.

**Steps to Reproduce:**
1. Run archetype generation with: `mvn archetype:generate ...`
2. Navigate to generated project
3. Run: `mvn clean compile`
4. See error

**Expected Behavior:**
Project should compile successfully.

**Actual Behavior:**
Compilation fails with error: [paste error message]

**Environment:**
- Java Version: 17
- Maven Version: 3.9.0
- OS: macOS 13.0
- Archetype Version: 1.0.0-SNAPSHOT
```

### Feature Requests

For new features or enhancements:
- **Describe the use case** and problem being solved
- **Explain the proposed solution** in detail
- **Consider alternatives** and their trade-offs
- **Provide examples** of how it would be used

### Documentation Improvements

Documentation contributions are highly valued:
- Fix typos and grammar errors
- Improve clarity and completeness
- Add examples and use cases
- Update outdated information

## Development Setup

### Prerequisites

- **Java 17+** (OpenJDK or Oracle JDK)
- **Maven 3.6.0+**
- **Git**
- **IDE** (IntelliJ IDEA, Eclipse, VS Code recommended)

### Getting Started

1. **Fork and clone** the repository:
```bash
git clone https://github.com/YOUR_USERNAME/maven-archetypes.git
cd maven-archetypes
```

2. **Build the project**:
```bash
mvn clean install
```

3. **Test archetype generation**:
```bash
mvn archetype:generate \
  -DarchetypeGroupId=com.martinvidec.archetypes \
  -DarchetypeArtifactId=spring-boot-web-archetype \
  -DarchetypeVersion=1.0.0-SNAPSHOT \
  -DgroupId=com.test \
  -DartifactId=test-project \
  -DinteractiveMode=false
```

4. **Verify generated project**:
```bash
cd test-project
mvn clean compile test
```

### Project Structure

```
maven-archetypes/
├── pom.xml                          # Parent POM
├── spring-boot-web-archetype/       # Web application archetype
├── spring-boot-jpa-archetype/       # JPA application archetype  
├── java-console-archetype/          # Console application archetype
├── java-library-archetype/         # Library archetype
├── docs/                           # Documentation
└── .github/                        # GitHub workflows
```

## Creating New Archetypes

### Archetype Structure

Follow Maven's standard archetype structure:

```
new-archetype/
├── pom.xml
└── src/
    └── main/
        └── resources/
            ├── archetype-resources/
            │   ├── pom.xml
            │   ├── README.md
            │   └── src/
            │       ├── main/java/
            │       └── test/java/
            └── META-INF/
                └── maven/
                    └── archetype-metadata.xml
```

### Implementation Steps

1. **Create archetype directory**:
```bash
mkdir my-new-archetype
cd my-new-archetype
```

2. **Create archetype POM**:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>com.martinvidec.archetypes</groupId>
        <artifactId>maven-archetypes-parent</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </parent>
    
    <artifactId>my-new-archetype</artifactId>
    <packaging>maven-archetype</packaging>
    <name>My New Archetype</name>
    
    <build>
        <extensions>
            <extension>
                <groupId>org.apache.maven.archetype</groupId>
                <artifactId>archetype-packaging</artifactId>
                <version>3.2.1</version>
            </extension>
        </extensions>
    </build>
</project>
```

3. **Add to parent POM modules**:
```xml
<modules>
    <!-- existing modules -->
    <module>my-new-archetype</module>
</modules>
```

4. **Create archetype-metadata.xml**:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<archetype-descriptor xmlns="http://maven.apache.org/plugins/maven-archetype-plugin/archetype-descriptor/1.0.0"
                      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                      xsi:schemaLocation="http://maven.apache.org/plugins/maven-archetype-plugin/archetype-descriptor/1.0.0
                      http://maven.apache.org/xsd/archetype-descriptor-1.0.0.xsd"
                      name="my-new-archetype">
    <requiredProperties>
        <requiredProperty key="groupId">
            <defaultValue>com.example</defaultValue>
        </requiredProperty>
        <requiredProperty key="artifactId">
            <defaultValue>my-project</defaultValue>
        </requiredProperty>
    </requiredProperties>
    
    <fileSets>
        <fileSet filtered="true" packaged="true">
            <directory>src/main/java</directory>
            <includes>
                <include>**/*.java</include>
            </includes>
        </fileSet>
        <!-- Add more fileSets as needed -->
    </fileSets>
</archetype-descriptor>
```

5. **Create template files** in `archetype-resources/`

6. **Test the archetype**:
```bash
mvn clean install
mvn archetype:generate \
  -DarchetypeGroupId=com.martinvidec.archetypes \
  -DarchetypeArtifactId=my-new-archetype \
  -DarchetypeVersion=1.0.0-SNAPSHOT
```

### Best Practices for New Archetypes

- **Follow naming conventions**: Use kebab-case for archetype names
- **Include comprehensive documentation**: README.md in generated projects
- **Provide working examples**: Generated code should compile and run
- **Add appropriate tests**: Unit and integration tests
- **Use modern dependencies**: Current versions of frameworks and libraries
- **Include configuration**: Sensible defaults for all settings
- **Consider different environments**: Development, testing, production

## Testing

### Types of Testing

1. **Archetype Generation Testing**: Verify archetypes generate correctly
2. **Generated Project Testing**: Ensure generated projects compile and run
3. **Integration Testing**: Test full workflow from generation to deployment

### Testing Commands

```bash
# Test all archetypes
mvn clean install

# Test specific archetype
cd spring-boot-web-archetype
mvn clean install

# Generate and test project
mvn archetype:generate \
  -DarchetypeGroupId=com.martinvidec.archetypes \
  -DarchetypeArtifactId=spring-boot-web-archetype \
  -DarchetypeVersion=1.0.0-SNAPSHOT \
  -DgroupId=com.test \
  -DartifactId=test-app \
  -DinteractiveMode=false

cd test-app
mvn clean compile test package
```

### Automated Testing

The project includes GitHub Actions workflows that:
- Build all archetypes
- Generate test projects from each archetype
- Compile and test generated projects
- Verify documentation links and formatting

## Coding Standards

### Java Code Style

- **Follow Google Java Style Guide**
- **Use meaningful variable and method names**
- **Add Javadoc for public APIs**
- **Keep methods focused and small**
- **Use modern Java features appropriately**

Example:
```java
/**
 * Service for processing user requests.
 */
@Service
public class UserService {
    
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    /**
     * Creates a new user with the given details.
     *
     * @param name the user's name
     * @param email the user's email address
     * @return the created user
     * @throws IllegalArgumentException if name or email is invalid
     */
    public User createUser(String name, String email) {
        validateInput(name, email);
        return userRepository.save(new User(name, email));
    }
}
```

### Template File Standards

- **Use proper variable substitution**: `${package}`, `${artifactId}`, etc.
- **Include meaningful comments**: Explain complex configurations
- **Provide sensible defaults**: Allow projects to work out-of-the-box
- **Follow framework conventions**: Use standard patterns and practices

### Documentation Standards

- **Use clear, concise language**
- **Provide examples for all features**
- **Keep documentation up-to-date**
- **Include troubleshooting sections**
- **Use proper Markdown formatting**

## Pull Request Process

### Before Submitting

1. **Test your changes thoroughly**
2. **Update documentation** as needed
3. **Follow coding standards**
4. **Ensure all tests pass**
5. **Write clear commit messages**

### Commit Message Format

Use conventional commit format:
```
type(scope): subject

body

footer
```

Examples:
```
feat(spring-boot-web): add Docker support to generated projects

Add Dockerfile and docker-compose.yml to generated Spring Boot web projects
to enable easy containerization and deployment.

Closes #42
```

```
fix(jpa-archetype): correct Hibernate configuration in application.yml

The hibernate.ddl-auto property was set to 'create' instead of 'validate'
in production profile, which could cause data loss.

Fixes #38
```

### PR Requirements

- **Clear description** of changes and motivation
- **Reference related issues** using keywords (Fixes #123)
- **Include test results** and verification steps
- **Update documentation** if needed
- **Keep changes focused** - one feature/fix per PR

### Review Process

1. **Automated checks** must pass (CI/CD pipeline)
2. **Code review** by maintainers
3. **Testing verification** of generated projects
4. **Documentation review** for accuracy and completeness
5. **Final approval** and merge

## Release Process

### Version Scheme

This project follows [Semantic Versioning](https://semver.org/):
- **MAJOR**: Breaking changes to archetype structure or generated projects
- **MINOR**: New archetypes or significant feature additions  
- **PATCH**: Bug fixes and minor improvements

### Release Steps

1. **Update version numbers** in all POMs
2. **Update CHANGELOG.md** with release notes
3. **Create release tag** and GitHub release
4. **Deploy to Maven Central** (when configured)
5. **Update documentation** with new version numbers

### Pre-release Checklist

- [ ] All tests pass
- [ ] Documentation is up-to-date
- [ ] Version numbers are updated
- [ ] CHANGELOG.md is updated
- [ ] Generated projects compile and run
- [ ] Breaking changes are documented

## Getting Help

- **Discord/Slack**: [Join our community chat]
- **GitHub Discussions**: [Ask questions and discuss ideas]
- **Email**: [maintainer@example.com]
- **Office Hours**: [Schedule time with maintainers]

## Recognition

Contributors are recognized in:
- **README.md contributors section**
- **Release notes** for significant contributions
- **GitHub contributors graph**
- **Special recognition** for major features or fixes

Thank you for contributing to make Maven development easier for everyone! 🚀