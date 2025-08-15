#!/bin/bash

# Compile Java files
echo "Compiling Java files..."
javac -d . src/main/java/*.java

if [ $? -eq 0 ]; then
    echo "Compilation successful!"
    echo "Run the application with: java BankingApp"
else
    echo "Compilation failed!"
    exit 1
fi