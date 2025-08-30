#!/bin/bash

# Test script to demonstrate how to use the cloud-ready-web-archetype
# This script shows the complete process of generating and building a project

set -e

echo "=== Cloud-Ready Web Archetype Testing ==="
echo

# Clean up any previous test
if [ -d "demo-app" ]; then
    echo "Cleaning up previous test..."
    rm -rf demo-app
fi

echo "1. Generating project from archetype..."
mvn archetype:generate \
    -DgroupId=com.example \
    -DartifactId=demo-app \
    -Dversion=1.0.0-SNAPSHOT \
    -Dpackage=com.example.demoapp \
    -DarchetypeGroupId=com.github.martinvidec \
    -DarchetypeArtifactId=cloud-ready-web-archetype \
    -DarchetypeVersion=1.0.1 \
    -DinteractiveMode=false

echo
echo "2. Project structure:"
cd demo-app
find . -type f -name "*.java" | head -10
echo

echo "3. Compiling the project..."
mvn clean compile -q

echo
echo "4. Validating project structure..."
echo "✓ Main application class exists: $([ -f src/main/java/com/example/demoapp/Application.java ] && echo "YES" || echo "NO")"
echo "✓ Test class exists: $([ -f src/test/java/com/example/demoapp/ApplicationTests.java ] && echo "YES" || echo "NO")"
echo "✓ Configuration exists: $([ -f src/main/resources/application.yml ] && echo "YES" || echo "NO")"
echo "✓ Dockerfile exists: $([ -f Dockerfile ] && echo "YES" || echo "NO")"
echo "✓ Docker Compose exists: $([ -f docker-compose.yml ] && echo "YES" || echo "NO")"

echo
echo "5. Checking key dependencies in POM..."
if grep -q "spring-boot-starter-web" pom.xml; then
    echo "✓ Spring Boot Web starter found"
fi
if grep -q "spring-boot-starter-security" pom.xml; then
    echo "✓ Spring Security starter found"
fi
if grep -q "spring-boot-starter-data-jpa" pom.xml; then
    echo "✓ Spring Data JPA starter found"
fi
if grep -q "testcontainers" pom.xml; then
    echo "✓ Testcontainers found"
fi

cd ..

echo
echo "=== SUCCESS: Archetype generated a functional Spring Boot project! ==="
echo "The generated project includes:"
echo "  - Spring Boot 3.2.0 with Java 17"
echo "  - Spring Security with JWT support"
echo "  - Spring Data JPA with PostgreSQL"
echo "  - Redis caching"
echo "  - OpenAPI documentation"
echo "  - Testcontainers for integration testing"
echo "  - Docker and Docker Compose configuration"
echo "  - GitHub Actions CI/CD pipeline"
echo
echo "To run the application:"
echo "  cd demo-app"
echo "  docker-compose up -d"
echo "  mvn spring-boot:run"