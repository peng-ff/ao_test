#!/bin/bash

# Shopping Website Startup Script

echo "=========================================="
echo "Starting Shopping Website..."
echo "=========================================="

cd "$(dirname "$0")"

# Check if JAR exists
if [ ! -f "target/shopping-website-1.0.0.jar" ]; then
    echo "JAR file not found! Building project first..."
    ./build_shopping.sh
fi

# Start the application
echo "Starting application on port 8080..."
java -jar target/shopping-website-1.0.0.jar

