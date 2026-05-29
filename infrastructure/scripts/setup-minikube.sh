#!/bin/bash

set -e

echo "================================"
echo "SmartOps-AI Minikube Setup"
echo "================================"

# Check if minikube is installed
if ! command -v minikube &> /dev/null; then
    echo "❌ Minikube not found. Installing..."
    echo "Please install Minikube from: https://minikube.sigs.k8s.io/docs/start/"
    exit 1
fi

echo "✅ Minikube found"

# Start minikube with docker driver
echo "🚀 Starting Minikube with docker driver and 4GB memory..."
minikube start \
    --driver=docker \
    --memory=4096 \
    --cpus=2 \
    --disk-size=30g \
    --addons=ingress,metrics-server \
    --kubernetes-version=latest

echo "✅ Minikube started successfully"

# Enable docker environment for building images
echo "🔧 Configuring Docker environment..."
eval $(minikube docker-env)

echo "✅ Docker environment configured"

echo "================================"
echo "Setup Complete!"
echo "================================"
echo ""
echo "Next steps:"
echo "1. Build Docker images: ./infrastructure/scripts/docker-build-all.sh"
echo "2. Deploy to Kubernetes: ./infrastructure/scripts/deploy-k8s.sh"
echo "3. Check status: kubectl get pods -n smartops"
