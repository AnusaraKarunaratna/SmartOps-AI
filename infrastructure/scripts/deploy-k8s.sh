#!/bin/bash

set -e

PROJECT_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
K8S_DIR="$PROJECT_ROOT/infrastructure/kubernetes"

echo "================================"
echo "Deploying to Kubernetes"
echo "================================"

# Create namespace
echo "📦 Creating namespace..."
kubectl apply -f "$K8S_DIR/namespace.yml"

# Create ConfigMaps and Secrets
echo "🔐 Creating ConfigMaps and Secrets..."
kubectl apply -f "$K8S_DIR/configmap.yml"
kubectl apply -f "$K8S_DIR/secrets.yml"

# Deploy databases
echo "💾 Deploying PostgreSQL..."
kubectl apply -f "$K8S_DIR/postgres.yml"

echo "🔴 Deploying Redis..."
kubectl apply -f "$K8S_DIR/redis.yml"

# Deploy Kafka
echo "📨 Deploying Kafka..."
kubectl apply -f "$K8S_DIR/kafka.yml"

echo "⏳ Waiting for PostgreSQL to be ready..."
kubectl wait --for=condition=Ready pod -l app=postgres -n smartops --timeout=300s || true

echo "⏳ Waiting for Kafka to be ready..."
kubectl wait --for=condition=Ready pod -l app=kafka-broker -n smartops --timeout=300s || true

# Deploy microservices
echo "🚀 Deploying microservices..."
kubectl apply -f "$K8S_DIR/auth-service.yml"
kubectl apply -f "$K8S_DIR/gateway-service.yml"
kubectl apply -f "$K8S_DIR/inventory-service.yml"
kubectl apply -f "$K8S_DIR/sales-service.yml"
kubectl apply -f "$K8S_DIR/notification-service.yml"
kubectl apply -f "$K8S_DIR/analytics-service.yml"
kubectl apply -f "$K8S_DIR/ai-prediction-service.yml"
kubectl apply -f "$K8S_DIR/frontend.yml"

echo "================================"
echo "Deployment in progress..."
echo "================================"
echo ""
echo "Check deployment status:"
echo "  kubectl get pods -n smartops"
echo "  kubectl get services -n smartops"
echo ""
echo "View logs:"
echo "  kubectl logs -n smartops -l app=<service-name>"
echo ""
echo "Access services with minikube:"
echo "  minikube service gateway-service -n smartops"
echo "  minikube service frontend -n smartops"
echo ""
