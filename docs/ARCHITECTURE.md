# SmartOps-AI Project Structure & Service Ports

## Microservices Ports
| Service | Port | Environment | Database | Tech Stack |
|---------|------|-------------|----------|-----------|
| Auth Service | 8081 | docker/k8s | PostgreSQL | Spring Boot, JWT |
| Gateway Service | 8082 | docker/k8s | - | Spring Cloud Gateway |
| Inventory Service | 8083 | docker/k8s | PostgreSQL | Spring Boot, Kafka |
| Notification Service | 8084 | docker/k8s | - | Spring Boot, Kafka |
| Sales Service | 8085 | docker/k8s | PostgreSQL | Spring Boot, Kafka |
| Analytics Service | 8086 | docker/k8s | PostgreSQL | Spring Boot |
| AI Prediction Service | 8090 | docker/k8s | - | FastAPI, Python |
| Frontend | 3000 | docker/k8s | - | Next.js, React |

## Infrastructure Services
| Service | Port | Docker | Kubernetes |
|---------|------|--------|-----------|
| PostgreSQL | 5432 | localhost | postgres-service |
| Redis | 6379 | localhost | redis-service |
| Zookeeper | 2181 | localhost | zookeeper-service |
| Kafka | 9092 | kafka | kafka-broker-headless |
| Kafka UI | 8080 | localhost | - |

## Directory Structure
```
SmartOps-AI/
├── auth-service/                 # Spring Boot - User Authentication
│   ├── src/
│   ├── pom.xml
│   ├── Dockerfile
│   └── src/main/resources/
│       ├── application.yml
│       ├── application-docker.yml
│       └── application-k8s.yml
├── gateway-service/              # Spring Cloud Gateway
│   ├── src/
│   ├── pom.xml
│   ├── Dockerfile
│   └── src/main/resources/
│       ├── application.yml
│       ├── application-docker.yml
│       └── application-k8s.yml
├── inventory-service/            # Inventory Management
├── sales-service/                # Sales Management
├── notification-service/         # Event-driven Notifications
├── analytics-service/            # Analytics & Reporting
├── ai-prediction-service/        # FastAPI - ML Predictions
├── frontend/                     # Next.js Frontend
├── infrastructure/
│   ├── docker/
│   │   ├── docker-compose-full.yml
│   │   ├── init-db.sql
│   │   └── .dockerignore
│   ├── kubernetes/
│   │   ├── namespace.yml
│   │   ├── configmap.yml
│   │   ├── secrets.yml
│   │   ├── postgres.yml
│   │   ├── redis.yml
│   │   ├── kafka.yml
│   │   ├── auth-service.yml
│   │   ├── gateway-service.yml
│   │   ├── inventory-service.yml
│   │   ├── sales-service.yml
│   │   ├── notification-service.yml
│   │   ├── analytics-service.yml
│   │   ├── ai-prediction-service.yml
│   │   └── frontend.yml
│   ├── scripts/
│   │   ├── setup-minikube.sh
│   │   ├── docker-build-all.sh
│   │   ├── deploy-k8s.sh
│   │   ├── docker-compose-up.sh
│   │   └── cleanup-k8s.sh
│   ├── kafka/
│   ├── monitoring/
│   └── docs/
├── DEPLOYMENT.md                 # Deployment Guide
└── README.md
```

## Docker Compose Features
- Multi-container orchestration
- PostgreSQL with automatic database initialization
- Redis for caching
- Kafka with Zookeeper
- All 8 microservices
- Health checks for all services
- Named volumes for data persistence
- Custom bridge network

## Kubernetes Deployment
- Namespace isolation (`smartops`)
- ConfigMaps for environment variables
- Secrets for sensitive data
- StatefulSet for Kafka
- Deployments with rolling updates
- Services for internal & external communication
- Persistent Volumes for databases
- Resource requests & limits
- Health probes (liveness & readiness)

## Common Issues & Solutions

### Docker Build Issues
```bash
# Clean previous builds
docker system prune -a

# Rebuild specific service
docker-compose -f infrastructure/docker/docker-compose-full.yml build --no-cache auth-service
```

### Kubernetes Pod Issues
```bash
# Describe pod for detailed info
kubectl describe pod auth-service-xxx -n smartops

# View logs
kubectl logs auth-service-xxx -n smartops

# Port forward for testing
kubectl port-forward -n smartops svc/auth-service 8081:8081
```

### Database Connection Issues
- Ensure PostgreSQL is running before services start
- Check credentials in secrets/configmaps
- Verify network connectivity between pods

## Production Checklist
- [ ] Update JWT secrets
- [ ] Change database passwords
- [ ] Enable SSL/TLS certificates
- [ ] Configure resource limits
- [ ] Setup monitoring & logging
- [ ] Enable RBAC
- [ ] Configure ingress with domain
- [ ] Setup backup strategy
- [ ] Configure auto-scaling
- [ ] Implement security policies
