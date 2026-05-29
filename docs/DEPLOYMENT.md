# SmartOps-AI Docker & Kubernetes Deployment Guide

## Overview
This guide covers deploying the SmartOps-AI microservices application using Docker and Kubernetes with Minikube.

## Architecture
- **Frontend**: Next.js React application
- **API Gateway**: Spring Cloud Gateway (port 8082)
- **Services**: 
  - Auth Service (8081)
  - Inventory Service (8083)
  - Sales Service (8085)
  - Notification Service (8084)
  - Analytics Service (8086)
  - AI Prediction Service (8090)
- **Message Queue**: Apache Kafka with Zookeeper
- **Databases**: PostgreSQL
- **Cache**: Redis

## Prerequisites
- Docker (version 20.10+)
- Kubernetes/Minikube (version 1.25+)
- kubectl (version 1.25+)
- 4GB+ RAM available
- Bash shell

## Quick Start with Docker Compose

### 1. Build All Docker Images
```bash
cd infrastructure/scripts
./docker-build-all.sh
```

### 2. Start Services
```bash
./docker-compose-up.sh
```

This will start:
- PostgreSQL database
- Redis cache
- Kafka broker + Zookeeper
- All microservices
- Frontend application

### 3. Access Applications
- **Frontend**: http://localhost:3000
- **API Gateway**: http://localhost:8082
- **Kafka UI**: http://localhost:8080

### 4. Stop Services
```bash
cd infrastructure/docker
docker-compose -f docker-compose-full.yml down
```

---

## Kubernetes Deployment with Minikube

### 1. Setup Minikube
```bash
cd infrastructure/scripts
./setup-minikube.sh
```

This will:
- Start Minikube with docker driver
- Allocate 4GB RAM and 2 CPUs
- Enable required addons (ingress, metrics-server)

### 2. Build Docker Images for Minikube
```bash
# Configure Docker environment for Minikube
eval $(minikube docker-env)

# Build images
./docker-build-all.sh
```

### 3. Deploy to Kubernetes
```bash
./deploy-k8s.sh
```

This will:
- Create `smartops` namespace
- Deploy PostgreSQL, Redis, Kafka
- Deploy all microservices
- Set up services and ingress

### 4. Monitor Deployment
```bash
# Watch pod status
kubectl get pods -n smartops -w

# View service status
kubectl get svc -n smartops

# Check logs
kubectl logs -n smartops -l app=auth-service
kubectl logs -n smartops -l app=gateway-service
```

### 5. Access Applications
```bash
# Frontend
minikube service frontend -n smartops

# Gateway
minikube service gateway-service -n smartops

# Or get the Minikube IP
minikube ip
# Then access: http://<minikube-ip>:3000
```

### 6. Clean Up
```bash
./cleanup-k8s.sh

# Stop Minikube
minikube stop
```

---

## Environment-Specific Configurations

### Docker Configuration
Services use `application-docker.yml` which connects to:
- PostgreSQL at `postgres:5432`
- Kafka at `kafka:9092`
- Redis at `redis:6379`

### Kubernetes Configuration
Services use `application-k8s.yml` which connects to:
- PostgreSQL at `postgres-service:5432`
- Kafka broker StatefulSet: `kafka-broker-0.kafka-broker-headless:9092`
- Redis at `redis-service:6379`

---

## Database Initialization
PostgreSQL automatically creates the following databases:
- `auth_service_db` - Auth service
- `inventory_db` - Inventory service
- `sales_db` - Sales service
- `analytics_db` - Analytics service
- `notification_db` - Notification service

All databases use default credentials:
- Username: `postgres`
- Password: `postgres`

---

## Kafka Topics
The following topics are auto-created:
- `inventory-events` (3 partitions, 1 replication factor)
- `order-events` (3 partitions, 1 replication factor)
- `notification-events` (3 partitions, 1 replication factor)

---

## Service Details

### Auth Service (Port 8081)
- User authentication & JWT token generation
- Database: PostgreSQL
- Endpoints:
  - `POST /api/auth/register` - Register new user
  - `POST /api/auth/login` - Login user
  - `POST /api/auth/refresh` - Refresh JWT token

### Gateway Service (Port 8082)
- API Gateway with JWT authentication
- Routes requests to appropriate microservices
- Endpoints:
  - `/api/auth/**` → Auth Service
  - `/api/inventory/**` → Inventory Service
  - `/api/sales/**` → Sales Service
  - `/api/notification/**` → Notification Service
  - `/api/analytics/**` → Analytics Service
  - `/api/ai/**` → AI Prediction Service

### Inventory Service (Port 8083)
- Product and inventory management
- Database: PostgreSQL
- Publishes `inventory-events` to Kafka

### Sales Service (Port 8085)
- Sales order management
- Database: PostgreSQL
- Publishes `order-events` to Kafka

### Notification Service (Port 8084)
- Consumes Kafka events
- Sends notifications
- No database (stateless)

### Analytics Service (Port 8086)
- Analytics and reporting
- Database: PostgreSQL

### AI Prediction Service (Port 8090)
- FastAPI Python service for ML predictions
- Endpoints:
  - `GET /` - Service status
  - `POST /predict` - Make predictions

### Frontend (Port 3000)
- Next.js React application
- Communicates with Gateway Service

---

## Troubleshooting

### Services won't start
```bash
# Check Docker logs
docker-compose -f infrastructure/docker/docker-compose-full.yml logs -f <service-name>

# Or Kubernetes logs
kubectl logs -n smartops -l app=<service-name> -f
```

### Database connection errors
```bash
# Verify PostgreSQL is running
docker exec smartops-postgres psql -U postgres -c "\l"

# Or in Kubernetes
kubectl exec -n smartops postgres-0 -- psql -U postgres -c "\l"
```

### Kafka not responding
```bash
# Check Kafka broker health
kafka-broker-api-versions.sh --bootstrap-servers localhost:9092

# Or in Kubernetes
kubectl exec -n smartops kafka-broker-0 -- kafka-broker-api-versions.sh --bootstrap-servers localhost:9092
```

### Port conflicts
If ports are already in use locally:
- Docker Compose: Edit `docker-compose-full.yml` to use different ports
- Kubernetes: Minikube handles port mapping automatically

---

## Performance Tuning

### For local development:
```yaml
# docker-compose-full.yml
resources:
  requests:
    memory: "256Mi"
    cpu: "250m"
```

### For production (Kubernetes):
Update resource limits in deployment YAML files:
```yaml
resources:
  requests:
    memory: "1Gi"
    cpu: "1000m"
  limits:
    memory: "2Gi"
    cpu: "2000m"
```

---

## Security Notes

### Default Credentials (Development Only)
- PostgreSQL: `postgres:postgres`
- JWT Secret: `smartops-secret-key-123456smartops-secret-key-123456`

### Production Recommendations
1. Use strong database passwords
2. Generate new JWT secret key
3. Enable RBAC in Kubernetes
4. Use private container registry
5. Implement network policies
6. Enable TLS/SSL for services
7. Use Kubernetes secrets for all credentials

---

## Monitoring

### Docker Compose
```bash
# View resource usage
docker stats

# Monitor logs in real-time
docker-compose -f infrastructure/docker/docker-compose-full.yml logs -f
```

### Kubernetes
```bash
# View metrics (requires metrics-server addon)
kubectl top nodes -n smartops
kubectl top pods -n smartops

# Use dashboard
minikube dashboard

# Real-time monitoring
watch kubectl get pods -n smartops
```

---

## Next Steps

1. **Configure DNS**: Set up domain routing for services
2. **Setup CI/CD**: Automate builds and deployments
3. **Add Monitoring**: Implement Prometheus + Grafana
4. **Add Logging**: Implement ELK stack
5. **Enable Auto-scaling**: Configure HPA for Kubernetes
6. **Setup Backup**: Implement database backup strategy

---

## Support & Documentation

For more information:
- [Docker Documentation](https://docs.docker.com/)
- [Kubernetes Documentation](https://kubernetes.io/docs/)
- [Minikube Documentation](https://minikube.sigs.k8s.io/docs/)
- [Kafka Documentation](https://kafka.apache.org/documentation/)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)

---
