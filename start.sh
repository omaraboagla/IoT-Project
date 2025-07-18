#!/bin/bash

set -e 
set -o pipefail  

echo "Cleaning and packaging the project..."
if ! mvn clean package -DskipTests; then
    echo "Maven build failed. Exiting."
    exit 1
fi

echo "Checking if Docker is running..."
if ! docker info > /dev/null 2>&1; then
    echo "Docker is not running. Please start Docker and try again."
    exit 1
fi

echo "Stopping existing Docker services..."
docker compose down || echo "No running services to stop."

echo "Building Docker image and starting services..."
if ! docker compose up --build -d; then
    echo "Failed to start Docker services. Exiting."
    exit 1
fi

echo "Waiting for MySQL to be healthy..."
MAX_RETRIES=10
RETRY=0
until docker exec iot-mysql mysqladmin ping -h"localhost" --silent; do
    sleep 2
    RETRY=$((RETRY+1))
    if [ "$RETRY" -ge "$MAX_RETRIES" ]; then
        echo "MySQL did not start in time. Exiting."
        exit 1
    fi
    echo "Waiting for MySQL to be ready ($RETRY/$MAX_RETRIES)..."
done

echo "MySQL is healthy."

echo "All services are running!"
