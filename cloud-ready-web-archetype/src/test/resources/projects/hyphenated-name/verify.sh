#!/bin/bash

set -e

echo "Verifying generated project with hyphenated artifact name..."

# Check that main application class exists with correct package
if [ ! -f "src/main/java/com/example/mytestapp/Application.java" ]; then
    echo "ERROR: Main application class not found in correct package"
    exit 1
fi

# Check that test class exists
if [ ! -f "src/test/java/com/example/mytestapp/ApplicationTests.java" ]; then
    echo "ERROR: Test class not found in correct package"
    exit 1
fi

# Verify the class names don't contain hyphens (which would be invalid)
if grep -q "class my-test-appApplication" src/main/java/com/example/mytestapp/Application.java; then
    echo "ERROR: Invalid class name with hyphens found"
    exit 1
fi

# Try to compile the project
echo "Compiling the generated project with hyphenated artifact name..."
mvn clean compile -q

echo "SUCCESS: Hyphenated artifact name test passed!"