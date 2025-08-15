#!/bin/bash

# Compile and run the banking application
echo "Starting SecureBank Banking Application..."
echo "========================================="

# Compile first
./compile.sh

if [ $? -eq 0 ]; then
    echo ""
    echo "Running the application..."
    java BankingApp
else
    echo "Cannot run application due to compilation errors."
fi