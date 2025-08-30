# Technical Documentation: Maven Archetypes Implementation

This document provides comprehensive technical specifications for implementing the Maven archetypes repository. A Copilot Agent should be able to create a complete implementation based on this documentation.

## Table of Contents

1. [Repository Structure](#repository-structure)
2. [Individual Archetype Implementation](#individual-archetype-implementation)
3. [Spring Boot Web Archetype](#spring-boot-web-archetype)
4. [Spring Boot JPA Archetype](#spring-boot-jpa-archetype)
5. [Java Console Archetype](#java-console-archetype)
6. [Java Library Archetype](#java-library-archetype)
7. [Build and Deployment](#build-and-deployment)
8. [Testing Strategy](#testing-strategy)
9. [CI/CD Integration](#cicd-integration)
10. [Usage Documentation](#usage-documentation)

## Repository Structure

The repository root should contain:

```
maven-archetypes/
├── README.md                           # Main repository documentation
├── concept.md                          # Project concept (already exists)
├── TECHNICAL_DOCUMENTATION.md         # This file
├── pom.xml                            # Parent POM for all archetypes
├── .gitignore                         # Git ignore patterns
├── .github/                           # GitHub workflows
│   └── workflows/
│       ├── build.yml                  # CI build workflow
│       └── release.yml                # Release workflow
├── spring-boot-web-archetype/         # Spring Boot web application archetype
├── spring-boot-jpa-archetype/         # Spring Boot JPA archetype
├── java-console-archetype/            # Console application archetype
├── java-library-archetype/           # Library archetype
└── docs/                              # Additional documentation
    ├── USAGE.md                       # Usage instructions
    └── CONTRIBUTING.md                # Contributing guidelines
```

## Individual Archetype Implementation

Each archetype follows Maven's standard archetype structure. Here's the detailed implementation for each:

### Common Configuration Files

#### Root pom.xml (Parent POM)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <groupId>com.martinvidec.archetypes</groupId>
    <artifactId>maven-archetypes-parent</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>pom</packaging>
    
    <name>Maven Archetypes Parent</name>
    <description>Diverse Maven Archetypes for modern Java development</description>
    <url>https://github.com/martinvidec/maven-archetypes</url>
    
    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven-archetype-plugin.version>3.2.1</maven-archetype-plugin.version>
    </properties>
    
    <modules>
        <module>spring-boot-web-archetype</module>
        <module>spring-boot-jpa-archetype</module>
        <module>java-console-archetype</module>
        <module>java-library-archetype</module>
    </modules>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-archetype-plugin</artifactId>
                <version>${maven-archetype-plugin.version}</version>
            </plugin>
        </plugins>
    </build>
    
    <scm>
        <connection>scm:git:https://github.com/martinvidec/maven-archetypes.git</connection>
        <developerConnection>scm:git:git@github.com:martinvidec/maven-archetypes.git</developerConnection>
        <url>https://github.com/martinvidec/maven-archetypes</url>
    </scm>
    
    <licenses>
        <license>
            <name>MIT License</name>
            <url>https://opensource.org/licenses/MIT</url>
            <distribution>repo</distribution>
        </license>
    </licenses>
    
    <developers>
        <developer>
            <id>martinvidec</id>
            <name>Martin Videc</name>
            <url>https://github.com/martinvidec</url>
        </developer>
    </developers>
</project>
```

#### .gitignore

```gitignore
# Maven
target/
pom.xml.tag
pom.xml.releaseBackup
pom.xml.versionsBackup
pom.xml.next
release.properties
dependency-reduced-pom.xml
buildNumber.properties
.mvn/timing.properties
.mvn/wrapper/maven-wrapper.jar

# IDE
.idea/
*.iml
*.ipr
*.iws
.vscode/
.settings/
.project
.classpath

# OS
.DS_Store
Thumbs.db

# Logs
*.log

# Temporary files
*.tmp
*.bak
*.swp
*~.nib
```

## Spring Boot Web Archetype

### Directory Structure

```
spring-boot-web-archetype/
├── pom.xml
└── src/
    └── main/
        └── resources/
            ├── archetype-resources/
            │   ├── pom.xml
            │   ├── README.md
            │   ├── .gitignore
            │   └── src/
            │       ├── main/
            │       │   ├── java/
            │       │   │   └── __packageInPathFormat__/
            │       │   │       ├── Application.java
            │       │   │       ├── controller/
            │       │   │       │   └── HealthController.java
            │       │   │       ├── service/
            │       │   │       │   └── GreetingService.java
            │       │   │       └── config/
            │       │   │           └── WebConfig.java
            │       │   └── resources/
            │       │       ├── application.yml
            │       │       └── static/
            │       │           └── index.html
            │       └── test/
            │           └── java/
            │               └── __packageInPathFormat__/
            │                   ├── ApplicationTests.java
            │                   └── controller/
            │                       └── HealthControllerTest.java
            └── META-INF/
                └── maven/
                    └── archetype-metadata.xml
```

### Spring Boot Web Archetype Files

#### pom.xml (Archetype)

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
    
    <artifactId>spring-boot-web-archetype</artifactId>
    <packaging>maven-archetype</packaging>
    
    <name>Spring Boot Web Application Archetype</name>
    <description>Maven archetype for Spring Boot web applications with REST API</description>
    
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

#### archetype-metadata.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<archetype-descriptor xmlns="http://maven.apache.org/plugins/maven-archetype-plugin/archetype-descriptor/1.0.0"
                      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                      xsi:schemaLocation="http://maven.apache.org/plugins/maven-archetype-plugin/archetype-descriptor/1.0.0
                      http://maven.apache.org/xsd/archetype-descriptor-1.0.0.xsd"
                      name="spring-boot-web">
    
    <requiredProperties>
        <requiredProperty key="groupId">
            <defaultValue>com.example</defaultValue>
        </requiredProperty>
        <requiredProperty key="artifactId">
            <defaultValue>my-spring-boot-app</defaultValue>
        </requiredProperty>
        <requiredProperty key="version">
            <defaultValue>1.0.0-SNAPSHOT</defaultValue>
        </requiredProperty>
        <requiredProperty key="package">
            <defaultValue>com.example.app</defaultValue>
        </requiredProperty>
        <requiredProperty key="spring-boot-version">
            <defaultValue>3.2.0</defaultValue>
        </requiredProperty>
    </requiredProperties>
    
    <fileSets>
        <fileSet filtered="true" packaged="true">
            <directory>src/main/java</directory>
            <includes>
                <include>**/*.java</include>
            </includes>
        </fileSet>
        <fileSet filtered="true" packaged="true">
            <directory>src/test/java</directory>
            <includes>
                <include>**/*.java</include>
            </includes>
        </fileSet>
        <fileSet filtered="true">
            <directory>src/main/resources</directory>
            <includes>
                <include>**/*</include>
            </includes>
        </fileSet>
        <fileSet>
            <directory></directory>
            <includes>
                <include>.gitignore</include>
                <include>README.md</include>
            </includes>
        </fileSet>
    </fileSets>
</archetype-descriptor>
```

#### Generated Project pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>${spring-boot-version}</version>
        <relativePath/>
    </parent>
    
    <groupId>${groupId}</groupId>
    <artifactId>${artifactId}</artifactId>
    <version>${version}</version>
    <packaging>jar</packaging>
    
    <name>${artifactId}</name>
    <description>Spring Boot Web Application</description>
    
    <properties>
        <java.version>17</java.version>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
    
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        
        <!-- Testing -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.11.0</version>
                <configuration>
                    <source>17</source>
                    <target>17</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.1.2</version>
            </plugin>
        </plugins>
    </build>
</project>
```

#### Application.java

```java
package ${package};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application class.
 */
@SpringBootApplication
public class Application {
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

#### HealthController.java

```java
package ${package}.controller;

import ${package}.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Health check and sample REST controller.
 */
@RestController
@RequestMapping("/api")
public class HealthController {
    
    private final GreetingService greetingService;
    
    @Autowired
    public HealthController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }
    
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
            "status", "UP",
            "service", "${artifactId}"
        ));
    }
    
    @GetMapping("/greeting")
    public ResponseEntity<Map<String, String>> greeting(@RequestParam(defaultValue = "World") String name) {
        String message = greetingService.createGreeting(name);
        return ResponseEntity.ok(Map.of("message", message));
    }
}
```

#### GreetingService.java

```java
package ${package}.service;

import org.springframework.stereotype.Service;

/**
 * Service for creating greeting messages.
 */
@Service
public class GreetingService {
    
    public String createGreeting(String name) {
        return "Hello, " + name + "!";
    }
}
```

#### WebConfig.java

```java
package ${package}.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web configuration for the application.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}
```

#### application.yml

```yaml
server:
  port: 8080
  servlet:
    context-path: /

spring:
  application:
    name: ${artifactId}
  
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics
  endpoint:
    health:
      show-details: when-authorized

logging:
  level:
    ${package}: INFO
    org.springframework.web: DEBUG
```

#### ApplicationTests.java

```java
package ${package};

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {
    
    @Test
    void contextLoads() {
        // Test that the application context loads successfully
    }
}
```

#### HealthControllerTest.java

```java
package ${package}.controller;

import ${package}.service.GreetingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HealthController.class)
class HealthControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private GreetingService greetingService;
    
    @Test
    void healthEndpoint_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }
    
    @Test
    void greetingEndpoint_ShouldReturnGreeting() throws Exception {
        when(greetingService.createGreeting(anyString())).thenReturn("Hello, Test!");
        
        mockMvc.perform(get("/api/greeting").param("name", "Test"))
                .andExpected(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello, Test!"));
    }
}
```

## Spring Boot JPA Archetype

### Directory Structure

```
spring-boot-jpa-archetype/
├── pom.xml
└── src/
    └── main/
        └── resources/
            ├── archetype-resources/
            │   ├── pom.xml
            │   ├── README.md
            │   ├── .gitignore
            │   └── src/
            │       ├── main/
            │       │   ├── java/
            │       │   │   └── __packageInPathFormat__/
            │       │   │       ├── Application.java
            │       │   │       ├── controller/
            │       │   │       │   └── UserController.java
            │       │   │       ├── service/
            │       │   │       │   └── UserService.java
            │       │   │       ├── repository/
            │       │   │       │   └── UserRepository.java
            │       │   │       ├── entity/
            │       │   │       │   └── User.java
            │       │   │       └── config/
            │       │   │           └── DatabaseConfig.java
            │       │   └── resources/
            │       │       ├── application.yml
            │       │       └── db/
            │       │           └── migration/
            │       │               └── V1__Create_user_table.sql
            │       └── test/
            │           └── java/
            │               └── __packageInPathFormat__/
            │                   ├── ApplicationTests.java
            │                   ├── repository/
            │                   │   └── UserRepositoryTest.java
            │                   └── service/
            │                       └── UserServiceTest.java
            └── META-INF/
                └── maven/
                    └── archetype-metadata.xml
```

### Key JPA Archetype Files

#### Generated Project pom.xml (JPA)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>${spring-boot-version}</version>
        <relativePath/>
    </parent>
    
    <groupId>${groupId}</groupId>
    <artifactId>${artifactId}</artifactId>
    <version>${version}</version>
    <packaging>jar</packaging>
    
    <name>${artifactId}</name>
    <description>Spring Boot Application with JPA</description>
    
    <properties>
        <java.version>17</java.version>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
    
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        <dependency>
            <groupId>org.flywaydb</groupId>
            <artifactId>flyway-core</artifactId>
        </dependency>
        
        <!-- Database -->
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>
        
        <!-- Testing -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>postgresql</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <groupId>org.flywaydb</groupId>
                <artifactId>flyway-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

#### User.java (Entity)

```java
package ${package}.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * User entity representing a user in the system.
 */
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(min = 2, max = 100)
    @Column(nullable = false)
    private String name;
    
    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Constructors
    public User() {
        this.createdAt = LocalDateTime.now();
    }
    
    public User(String name, String email) {
        this();
        this.name = name;
        this.email = email;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
```

## Java Console Archetype

### Directory Structure

```
java-console-archetype/
├── pom.xml
└── src/
    └── main/
        └── resources/
            ├── archetype-resources/
            │   ├── pom.xml
            │   ├── README.md
            │   ├── .gitignore
            │   └── src/
            │       ├── main/
            │       │   ├── java/
            │       │   │   └── __packageInPathFormat__/
            │       │   │       ├── Application.java
            │       │   │       ├── command/
            │       │   │       │   └── CommandProcessor.java
            │       │   │       └── util/
            │       │   │           └── InputValidator.java
            │       │   └── resources/
            │       │       └── application.properties
            │       └── test/
            │           └── java/
            │               └── __packageInPathFormat__/
            │                   ├── ApplicationTest.java
            │                   └── command/
            │                       └── CommandProcessorTest.java
            └── META-INF/
                └── maven/
                    └── archetype-metadata.xml
```

## Java Library Archetype

### Directory Structure

```
java-library-archetype/
├── pom.xml
└── src/
    └── main/
        └── resources/
            ├── archetype-resources/
            │   ├── pom.xml
            │   ├── README.md
            │   ├── LICENSE
            │   ├── .gitignore
            │   └── src/
            │       ├── main/
            │       │   ├── java/
            │       │   │   └── __packageInPathFormat__/
            │       │   │       ├── ${artifactId}Library.java
            │       │   │       └── util/
            │       │   │           └── Helper.java
            │       │   └── resources/
            │       └── test/
            │           └── java/
            │               └── __packageInPathFormat__/
            │                   └── ${artifactId}LibraryTest.java
            └── META-INF/
                └── maven/
                    └── archetype-metadata.xml
```

## Build and Deployment

### Build Commands

To build all archetypes:
```bash
mvn clean install
```

To build a specific archetype:
```bash
cd spring-boot-web-archetype
mvn clean install
```

### Local Testing

To test an archetype locally:
```bash
mvn archetype:generate \
  -DarchetypeGroupId=com.martinvidec.archetypes \
  -DarchetypeArtifactId=spring-boot-web-archetype \
  -DarchetypeVersion=1.0.0-SNAPSHOT \
  -DgroupId=com.example \
  -DartifactId=my-test-app \
  -Dversion=1.0.0-SNAPSHOT \
  -Dpackage=com.example.myapp \
  -DinteractiveMode=false
```

### Deployment to Maven Central

Configuration for deployment requires:
1. GPG signing setup
2. Sonatype OSSRH account
3. Maven settings configuration

## Testing Strategy

### Archetype Testing

Each archetype should include:
1. **Unit tests** for generated code
2. **Integration tests** for the archetype generation process
3. **Build verification** - ensure generated projects compile and run

### Testing Framework Integration

- **JUnit 5** for unit testing
- **Mockito** for mocking
- **Spring Boot Test** for Spring-based archetypes
- **Testcontainers** for integration testing with databases

## CI/CD Integration

### GitHub Actions Workflows

#### .github/workflows/build.yml

```yaml
name: Build and Test

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest
    
    strategy:
      matrix:
        java-version: [17, 21]
    
    steps:
    - uses: actions/checkout@v4
    
    - name: Set up JDK ${{ matrix.java-version }}
      uses: actions/setup-java@v4
      with:
        java-version: ${{ matrix.java-version }}
        distribution: 'temurin'
    
    - name: Cache Maven packages
      uses: actions/cache@v3
      with:
        path: ~/.m2
        key: ${{ runner.os }}-m2-${{ hashFiles('**/pom.xml') }}
        restore-keys: ${{ runner.os }}-m2
    
    - name: Build with Maven
      run: mvn clean compile
    
    - name: Run tests
      run: mvn test
    
    - name: Test archetype generation
      run: |
        cd target
        mvn archetype:generate \
          -DarchetypeGroupId=com.martinvidec.archetypes \
          -DarchetypeArtifactId=spring-boot-web-archetype \
          -DarchetypeVersion=1.0.0-SNAPSHOT \
          -DgroupId=com.example \
          -DartifactId=test-app \
          -Dversion=1.0.0-SNAPSHOT \
          -DinteractiveMode=false
        cd test-app
        mvn clean compile test
```

## Usage Documentation

### Quick Start Guide

1. **Generate a Spring Boot Web Application**:
```bash
mvn archetype:generate \
  -DarchetypeGroupId=com.martinvidec.archetypes \
  -DarchetypeArtifactId=spring-boot-web-archetype \
  -DgroupId=com.mycompany \
  -DartifactId=my-web-app \
  -Dpackage=com.mycompany.webapp
```

2. **Generate a Spring Boot JPA Application**:
```bash
mvn archetype:generate \
  -DarchetypeGroupId=com.martinvidec.archetypes \
  -DarchetypeArtifactId=spring-boot-jpa-archetype \
  -DgroupId=com.mycompany \
  -DartifactId=my-jpa-app \
  -Dpackage=com.mycompany.jpaapp
```

### Customization Options

Each archetype supports the following parameters:
- `groupId`: Maven group ID for the generated project
- `artifactId`: Maven artifact ID and project name
- `version`: Initial version (default: 1.0.0-SNAPSHOT)
- `package`: Base package for Java classes
- `spring-boot-version`: Spring Boot version to use (for Spring Boot archetypes)

### Generated Project Structure

After generation, each project includes:
- **Complete Maven configuration**
- **Sample source code** demonstrating best practices
- **Unit and integration tests**
- **Documentation** (README.md)
- **Configuration files** (application.yml, etc.)
- **Build and deployment setup**

## Implementation Checklist

To implement this specification:

- [ ] Create root `pom.xml` with parent configuration
- [ ] Set up `.gitignore` file
- [ ] Create `spring-boot-web-archetype` with all specified files
- [ ] Create `spring-boot-jpa-archetype` with all specified files
- [ ] Create `java-console-archetype` with all specified files
- [ ] Create `java-library-archetype` with all specified files
- [ ] Set up GitHub Actions workflows
- [ ] Create comprehensive README.md
- [ ] Add usage documentation
- [ ] Test each archetype generation
- [ ] Verify generated projects compile and run
- [ ] Add integration tests for archetype generation process

This documentation provides complete specifications for implementing a production-ready Maven archetypes repository.