#!/bin/bash

# Compile the Java application
# Requires Java 21 or later

echo "Compiling Restaurant Management System..."
javac -d bin src/com/restaurant/**/*.java src/com/restaurant/*.java

if [ $? -eq 0 ]; then
    echo "Compilation successful!"
    echo ""
    echo "To run the application:"
    echo "  java -cp bin com.restaurant.RestaurantApp"
else
    echo "Compilation failed!"
    exit 1
fi
