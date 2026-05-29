# SmartOps-AI: Quick Reference Card

## 🚀 QUICK START COMMANDS

### Docker Compose
```bash
cd infrastructure/scripts
./docker-build-all.sh
./docker-compose-up.sh

# Access: http://localhost:3000
```

### Kubernetes/Minikube
```bash
cd infrastructure/scripts
./setup-minikube.sh
eval $(minikube docker-env)
./docker-build-all.sh
./deploy-k8s.sh

# Access: minikube service frontend -n smartops
```

---

## 📍 SERVICE PORTS

```
Frontend            3000
API Gateway         8082
Auth Service        8081
Inventory Service   8083
Sales Service       8085
Notification        8084
Analytics           8086
AI Prediction       8090
PostgreSQL          5432
Redis               6379
Kafka               9092
Kafka UI            8080
```

---

## 📂 KEY FILES LOCATION

```
Dockerfiles:
  - auth-service/Dockerfile
  - gateway-service/Dockerfile
  - inventory-service/Dockerfile
  - sales-service/Dockerfile
  - notification-service/Dockerfile
  - analytics-service/Dockerfile
  - ai-prediction-service/Dockerfile
  - frontend/Dockerfile

Docker Setup:
  - infrastructure/docker/docker-compose-full.yml
  - infrastructure/docker/init-db.sql

Kubernetes:
  - infrastructure/kubernetes/*.yml (14 files)

Scripts:
  - infrastructure/scripts/*.sh (5 files)

Documentation:
  - DEPLOYMENT.md
  - SETUP.md
  - ARCHITECTURE.md
  - CHANGES_SUMMARY.md
  - DELIVERABLES.md
```

---

## 🔍 USEFUL COMMANDS

### Docker Compose
```bash
# Build images
docker-compose -f infrastructure/docker/docker-compose-full.yml build

# Start services
docker-compose -f infrastructure/docker/docker-compose-full.yml up -d

# View logs
docker-compose -f infrastructure/docker/docker-compose-full.yml logs -f auth-service

# Stop services
docker-compose -f infrastructure/docker/docker-compose-full.yml down

# Check status
docker ps
```

### Kubernetes
```bash
# Apply configurations
kubectl apply -f infrastructure/kubernetes/namespace.yml
kubectl apply -f infrastructure/kubernetes/configmap.yml
kubectl apply -f infrastructure/kubernetes/secrets.yml
kubectl apply -f infrastructure/kubernetes/postgres.yml
kubectl apply -f infrastructure/kubernetes/redis.yml
kubectl apply -f infrastructure/kubernetes/kafka.yml
kubectl apply -f infrastructure/kubernetes/*-service.yml

# Check status
kubectl get pods -n smartops
kubectl get services -n smartops
kubectl get pvc -n smartops

# View logs
kubectl logs -n smartops pod/auth-service-xxxx
kubectl logs -n smartops -l app=auth-service

# Port forward
kubectl port-forward -n smartops svc/auth-service 8081:8081

# Describe resources
kubectl describe pod auth-service-xxxx -n smartops

# Delete resources
kubectl delete -f infrastructure/kubernetes/
kubectl delete namespace smartops
```

---

## 🐳 DOCKER INFO

### Image Sizes
```
auth-service:           386MB
gateway-service:        250MB
inventory-service:      260MB
sales-service:          250MB
notification-service:   220MB
analytics-service:      260MB
ai-prediction-service:  800MB (ML libraries)
frontend:               300MB
```

### Build Strategy
- Multi-stage builds (Maven in builder stage)
- Alpine Linux base images
- Production-ready configurations
- Health checks integrated

---

## ☸️ KUBERNETES INFO

### Replicas
```
Deployments:           2 replicas each (services)
Analytics Service:     1 replica
AI Service:           1 replica
Frontend:             2 replicas
StatefulSets:         3 replicas (Kafka brokers)
Kafka Brokers:        3
```

### Resource Limits
```
Services:    512Mi memory, 250m CPU
AI Service:  512Mi-2Gi memory, 500m-1000m CPU
Databases:   256-512Mi memory
```

---

## 🗄️ DATABASE CREDENTIALS

### Docker & Kubernetes
```
Username: postgres
Password: postgres
Databases:
  - auth_service_db
  - inventory_db
  - sales_db
  - analytics_db
  - notification_db
```

---

## 🔐 JWT CONFIGURATION

```
Secret: smartops-secret-key-123456smartops-secret-key-123456
Expiration: 86400000 (24 hours)

Note: Change in production!
```

---

## 📡 KAFKA TOPICS

```
inventory-events       (3 partitions, 1 replica)
order-events          (3 partitions, 1 replica)
notification-events   (3 partitions, 1 replica)
```

### Bootstrap Servers
```
Docker:      kafka:9092
Kubernetes:  kafka-broker-0.kafka-broker-headless:9092
            kafka-broker-1.kafka-broker-headless:9092
            kafka-broker-2.kafka-broker-headless:9092
```

---

## 🧪 HEALTH CHECKS

### Docker Compose
```
HTTP GET:  /actuator/health
Port:      8081-8090 (services)
Interval:  10-15 seconds
Timeout:   5 seconds
```

### Kubernetes
```
Liveness:    /actuator/health/liveness
Readiness:   /actuator/health/readiness
Interval:    5-10 seconds
Initial Delay: 10-30 seconds
```

---

## 🔧 ENVIRONMENT PROFILES

### Docker
```
SPRING_PROFILES_ACTIVE=docker
Uses: application-docker.yml
```

### Kubernetes
```
SPRING_PROFILES_ACTIVE=k8s
Uses: application-k8s.yml
```

---

## 📊 MONITORING & LOGS

### Docker Compose
```bash
# Overall status
docker ps

# Service health
docker inspect --format='{{.State.Health.Status}}' smartops-auth

# Real-time logs
docker logs -f smartops-auth

# Resource usage
docker stats
```

### Kubernetes
```bash
# Pod status
kubectl get pods -n smartops

# Resource usage
kubectl top pods -n smartops
kubectl top nodes

# Event logs
kubectl get events -n smartops

# Dashboard
minikube dashboard
```

---

## ⚠️ TROUBLESHOOTING

### Services won't start
```bash
# Check logs
docker logs smartops-[service] -f
kubectl logs -n smartops -l app=[service] -f

# Check resources
docker ps
kubectl get pods -n smartops
```

### Database connection failed
```bash
# Verify PostgreSQL running
docker exec smartops-postgres pg_isready -U postgres
kubectl exec -n smartops postgres-0 -- pg_isready -U postgres
```

### Port already in use
```bash
# Find process
lsof -i :[port]

# Kill process (macOS)
kill -9 [PID]

# Or use different port in docker-compose-full.yml
```

### Kafka not responding
```bash
# Check broker
docker exec smartops-kafka kafka-broker-api-versions.sh --bootstrap-servers kafka:9092

# Check K8s
kubectl exec -n smartops kafka-broker-0 -- kafka-broker-api-versions.sh --bootstrap-servers localhost:9092
```

---

## 📚 DOCUMENTATION FILES

| File | Purpose | Size |
|------|---------|------|
| SETUP.md | Quick start | 10 KB |
| DEPLOYMENT.md | Full guide | 8 KB |
| ARCHITECTURE.md | System design | 4 KB |
| CHANGES_SUMMARY.md | Change log | 23 KB |
| DELIVERABLES.md | Completion | 22 KB |
| README_DOCKER_K8S.md | Overview | 8 KB |

---

## ✅ VERIFICATION CHECKLIST

Before deployment:
- [ ] All Dockerfiles created (8)
- [ ] All K8s manifests created (14)
- [ ] All scripts executable
- [ ] Docker build successful
- [ ] Maven builds pass
- [ ] Configuration files updated
- [ ] Documentation reviewed

---

## 🎯 PRODUCTION CHECKLIST

Before production:
- [ ] Change database passwords
- [ ] Generate new JWT secret
- [ ] Configure TLS/SSL
- [ ] Enable RBAC
- [ ] Use private registry
- [ ] Setup monitoring
- [ ] Setup logging
- [ ] Configure auto-scaling
- [ ] Setup backup
- [ ] Load test

---

## 🚀 NEXT STEPS

1. Read SETUP.md for quick start
2. Build images: `./docker-build-all.sh`
3. Test Docker Compose
4. Test Kubernetes/Minikube
5. Configure production settings
6. Deploy to production
7. Monitor and maintain

---

<div align="center">

**Version 1.0** | **May 29, 2026**

</div>
