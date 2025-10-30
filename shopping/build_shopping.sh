#!/bin/bash

# Shopping Website Build Script

echo "=========================================="
echo "Building Shopping Website..."
echo "=========================================="

cd "$(dirname "$0")"

# Clean and build with Maven
echo "Running Maven clean package..."
mvn clean package -DskipTests

if [ $? -eq 0 ]; then
    echo "=========================================="
    echo "Build completed successfully!"
    echo "JAR file location: target/shopping-website-1.0.0.jar"
    echo "=========================================="
else
    echo "=========================================="
    echo "Build failed!"
    echo "=========================================="
    exit 1
fi
