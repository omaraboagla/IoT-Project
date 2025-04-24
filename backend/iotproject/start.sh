#!/bin/bash

echo "Cleaning and packaging the project"
mvn clean package -DskipTests

echo "Building Docker image and starting services"
docker compose down
docker compose up --build -d

echo "All services are running!"
