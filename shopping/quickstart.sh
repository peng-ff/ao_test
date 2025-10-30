#!/bin/bash

# Quick Start Script for Shopping Website

echo "=========================================="
echo "Shopping Website Quick Start"
echo "=========================================="

cd "$(dirname "$0")"

# Step 1: Initialize database
echo "Step 1: Initializing database..."
read -p "Have you configured MySQL? (y/n): " mysql_ready

if [ "$mysql_ready" = "y" ]; then
    echo "Running database initialization..."
    ./init_database.sh
else
    echo "Please configure MySQL first and run ./init_database.sh manually"
fi

# Step 2: Build project
echo ""
echo "Step 2: Building project..."
./build_shopping.sh

# Step 3: Start application
echo ""
echo "Step 3: Starting application..."
read -p "Start the application now? (y/n): " start_now

if [ "$start_now" = "y" ]; then
    ./start_shopping.sh
else
    echo "You can start the application later with: ./start_shopping.sh"
fi

echo "=========================================="
echo "Quick Start Complete!"
echo "=========================================="
