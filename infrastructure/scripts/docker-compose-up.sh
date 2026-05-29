#!/bin/bash

set -e

PROJECT_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
DOCKER_DIR="$PROJECT_ROOT/infrastructure/docker"

echo "================================"
echo "Starting Docker Compose"
echo "================================"

cd "$DOCKER_DIR"

# Build and start services
echo "🐳 Starting services with Docker Compose..."
docker-compose -f docker-compose-full.yml up -d

echo "⏳ Waiting for services to be healthy..."
sleep 5

# Check service health
echo ""
echo "================================"
echo "Service Status"
echo "================================"

services=(
    "postgres:5432"
    "redis:6379"
    "zookeeper:2181"
    "kafka:9092"
    "auth-service:8081"
    "gateway-service:8082"
    "inventory-service:8083"
    "notification-service:8084"
    "sales-service:8085"
    "analytics-service:8086"
    "ai-prediction-service:8090"
    "frontend:3000"
)

for service in "${services[@]}"; do
    name="${service%:*}"
    port="${service##*:}"
    if nc -z localhost "$port" 2>/dev/null; then
        echo "✅ $name (port $port) - Ready"
    else
        echo "⏳ $name (port $port) - Starting..."
    fi
done

echo ""
echo "================================"
echo "Docker Compose Started!"
echo "================================"
echo ""
echo "Services URLs:"
echo "  Frontend:     http://localhost:3000"
echo "  Gateway:      http://localhost:8082"
echo "  Auth:         http://localhost:8081"
echo "  Inventory:    http://localhost:8083"
echo "  Sales:        http://localhost:8085"
echo "  Notification: http://localhost:8084"
echo "  Analytics:    http://localhost:8086"
echo "  AI Service:   http://localhost:8090"
echo "  Kafka UI:     http://localhost:8080"
echo ""
echo "To stop services: docker-compose -f docker-compose-full.yml down"
echo ""
