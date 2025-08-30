#!/bin/bash

set -e

echo "Verifying generated project structure..."

# Check that main application class exists
if [ ! -f "src/main/java/com/example/testapp/Application.java" ]; then
    echo "ERROR: Main application class not found"
    exit 1
fi

# Check that test class exists
if [ ! -f "src/test/java/com/example/testapp/ApplicationTests.java" ]; then
    echo "ERROR: Test class not found"
    exit 1
fi

# Check that key configuration files exist
if [ ! -f "src/main/resources/application.yml" ]; then
    echo "ERROR: application.yml not found"
    exit 1
fi

# Check that pom.xml exists and contains expected dependencies
if [ ! -f "pom.xml" ]; then
    echo "ERROR: pom.xml not found"
    exit 1
fi

# Verify Spring Boot starter dependencies are present
if ! grep -q "spring-boot-starter-web" pom.xml; then
    echo "ERROR: spring-boot-starter-web dependency not found"
    exit 1
fi

if ! grep -q "spring-boot-starter-data-jpa" pom.xml; then
    echo "ERROR: spring-boot-starter-data-jpa dependency not found"
    exit 1
fi

# Check that Docker files exist
if [ ! -f "Dockerfile" ]; then
    echo "ERROR: Dockerfile not found"
    exit 1
fi

if [ ! -f "docker-compose.yml" ]; then
    echo "ERROR: docker-compose.yml not found"
    exit 1
fi

# Try to compile the project
echo "Compiling the generated project..."
mvn clean compile -q

echo "SUCCESS: All verifications passed!"