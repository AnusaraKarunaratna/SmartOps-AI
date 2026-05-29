#!/bin/bash

set -e

PROJECT_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
DOCKER_DIR="$PROJECT_ROOT/infrastructure/docker"

echo "Building Docker Images"

cd "$PROJECT_ROOT"

# Build Auth Service
echo "🔨 Building auth-service..."
cd "$PROJECT_ROOT/auth-service"
docker build -t auth-service:latest .
echo "auth-service built"

# Build Gateway Service
echo "🔨 Building gateway-service..."
cd "$PROJECT_ROOT/gateway-service"
docker build -t gateway-service:latest .
echo "gateway-service built"

# Build Inventory Service
echo "🔨 Building inventory-service..."
cd "$PROJECT_ROOT/inventory-service"
docker build -t inventory-service:latest .
echo "inventory-service built"

# Build Sales Service
echo "🔨 Building sales-service..."
cd "$PROJECT_ROOT/sales-service"
docker build -t sales-service:latest .
echo "sales-service built"

# Build Notification Service
echo "🔨 Building notification-service..."
cd "$PROJECT_ROOT/notification-service"
docker build -t notification-service:latest .
echo "notification-service built"

# Build Analytics Service
echo "🔨 Building analytics-service..."
cd "$PROJECT_ROOT/analytics-service"
docker build -t analytics-service:latest .
echo "analytics-service built"

# Build AI Prediction Service
echo "🔨 Building ai-prediction-service..."
cd "$PROJECT_ROOT/ai-prediction-service"
docker build -t ai-prediction-service:latest .
echo "ai-prediction-service built"

# Build Frontend
echo "🔨 Building frontend..."
cd "$PROJECT_ROOT/frontend"
docker build -t frontend:latest .
echo "frontend built"

echo "All images built successfully!"

echo ""
echo "Images created:"
docker images | grep -E "auth-service|gateway-service|inventory-service|sales-service|notification-service|analytics-service|ai-prediction-service|frontend" || true
