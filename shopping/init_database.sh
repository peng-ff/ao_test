#!/bin/bash

# Initialize Shopping Database Script

echo "=========================================="
echo "Initializing Shopping Database..."
echo "=========================================="

cd "$(dirname "$0")"

# Run SQL script
mysql -u root -p < database/init_shopping.sql

if [ $? -eq 0 ]; then
    echo "=========================================="
    echo "Database initialized successfully!"
    echo "=========================================="
else
    echo "=========================================="
    echo "Database initialization failed!"
    echo "Please check your MySQL connection."
    echo "=========================================="
    exit 1
fi
