#!/bin/bash

set -e

PROJECT_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
K8S_DIR="$PROJECT_ROOT/infrastructure/kubernetes"

echo "================================"
echo "Cleaning up Kubernetes"
echo "================================"

# Delete all services and deployments
echo "🧹 Removing deployments..."
kubectl delete deployment -n smartops --all || true

# Delete services
echo "🧹 Removing services..."
kubectl delete service -n smartops --all || true

# Delete statefulsets
echo "🧹 Removing statefulsets..."
kubectl delete statefulset -n smartops --all || true

# Delete PVCs
echo "🧹 Removing persistent volumes..."
kubectl delete pvc -n smartops --all || true

# Delete namespace
echo "🧹 Removing namespace..."
kubectl delete namespace smartops || true

echo "================================"
echo "Cleanup Complete!"
echo "================================"
echo ""
