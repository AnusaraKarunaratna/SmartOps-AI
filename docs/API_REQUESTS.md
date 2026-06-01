# SmartOps AI - API Request Guide

This guide provides sample API requests for the SmartOps AI microservices. You can use these with Postman, cURL, or any HTTP client.

## Base URLs

- **Auth Service**: `http://localhost:8081`
- **Inventory Service**: `http://localhost:8083`
- **Gateway**: `http://localhost:8082`

---

## Auth Service API

### 1. Register User (Sign Up)

**Endpoint**: `POST /api/auth/register`

**Description**: Register a new user account in the system.

#### cURL Request

```bash
curl -X POST http://localhost:8081/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "email": "john@example.com",
    "password": "SecurePassword123!",
    "role": "ADMIN",
    "branchId": "1"
  }'
```

#### HTTP Request

```http
POST /api/auth/register HTTP/1.1
Host: localhost:8081
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "SecurePassword123!",
  "role": "ADMIN",
  "branchId": "1"
}
```

#### Request Body

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| username | String | Yes | Unique username for login |
| email | String | Yes | User email address |
| password | String | Yes | Secure password (min 8 chars recommended) |
| role | String | Yes | User role (ADMIN, USER, MANAGER, etc.) |
| branchId | String | Yes | Associated branch ID |

#### Sample Response (200 OK)

```json
"User registered successfully with ID: 1"
```

#### Error Responses

```json
// 400 Bad Request - Validation error
"Username already exists"

// 500 Internal Server Error
"Error registering user"
```

---

### 2. Login User

**Endpoint**: `POST /api/auth/login`

**Description**: Authenticate user and get access token.

#### cURL Request

```bash
curl -X POST "http://localhost:8081/api/auth/login?username=john_doe&password=SecurePassword123!" \
  -H "Content-Type: application/json"
```

#### HTTP Request

```http
POST /api/auth/login?username=john_doe&password=SecurePassword123! HTTP/1.1
Host: localhost:8081
Content-Type: application/json
```

#### Query Parameters

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| username | String | Yes | Username to login |
| password | String | Yes | User password |

#### Sample Response (200 OK)

```json
"Login successful. JWT Token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2huX2RvZSIsImlhdCI6MTUxNjIzOTAyMn0..."
```

#### Error Responses

```json
// 401 Unauthorized
"Invalid credentials"

// 404 Not Found
"User not found"
```

---

## Inventory Service API

### 1. Create Inventory

**Endpoint**: `POST /api/inventory`

**Description**: Create a new inventory record for a product in a branch.

#### cURL Request

```bash
curl -X POST http://localhost:8083/api/inventory \
  -H "Content-Type: application/json" \
  -d '{
    "branchId": 1,
    "productId": 101,
    "quantity": 50,
    "minimumStock": 10
  }'
```

#### HTTP Request

```http
POST /api/inventory HTTP/1.1
Host: localhost:8083
Content-Type: application/json

{
  "branchId": 1,
  "productId": 101,
  "quantity": 50,
  "minimumStock": 10
}
```

#### Request Body

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| branchId | Long | Yes | Branch identifier |
| productId | Long | Yes | Product identifier |
| quantity | Integer | Yes | Current stock quantity |
| minimumStock | Integer | Yes | Minimum threshold for stock alert |

#### Sample Response (201 Created)

```json
{
  "id": 1,
  "branchId": 1,
  "productId": 101,
  "quantity": 50,
  "minimumStock": 10
}
```

---

### 2. Get All Inventory Records

**Endpoint**: `GET /api/inventory`

**Description**: Retrieve all inventory records.

#### cURL Request

```bash
curl -X GET http://localhost:8083/api/inventory \
  -H "Content-Type: application/json"
```

#### HTTP Request

```http
GET /api/inventory HTTP/1.1
Host: localhost:8083
Content-Type: application/json
```

#### Sample Response (200 OK)

```json
[
  {
    "id": 1,
    "branchId": 1,
    "productId": 101,
    "quantity": 50,
    "minimumStock": 10
  },
  {
    "id": 2,
    "branchId": 2,
    "productId": 102,
    "quantity": 75,
    "minimumStock": 15
  }
]
```

---

### 3. Get Inventory by ID

**Endpoint**: `GET /api/inventory/{id}`

**Description**: Retrieve a specific inventory record by ID.

#### cURL Request

```bash
curl -X GET http://localhost:8083/api/inventory/1 \
  -H "Content-Type: application/json"
```

#### HTTP Request

```http
GET /api/inventory/1 HTTP/1.1
Host: localhost:8083
Content-Type: application/json
```

#### Path Parameters

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| id | Long | Yes | Inventory record ID |

#### Sample Response (200 OK)

```json
{
  "id": 1,
  "branchId": 1,
  "productId": 101,
  "quantity": 50,
  "minimumStock": 10
}
```

#### Error Responses

```json
// 404 Not Found
"Inventory record not found"
```

---

### 4. Update Inventory Stock

**Endpoint**: `POST /api/inventory/update`

**Description**: Update the stock quantity for a product in a specific branch.

#### cURL Request

```bash
curl -X POST "http://localhost:8083/api/inventory/update?branchId=1&productId=101&qty=15" \
  -H "Content-Type: application/json"
```

#### HTTP Request

```http
POST /api/inventory/update?branchId=1&productId=101&qty=15 HTTP/1.1
Host: localhost:8083
Content-Type: application/json
```

#### Query Parameters

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| branchId | Long | Yes | Branch identifier |
| productId | Long | Yes | Product identifier |
| qty | Integer | Yes | Quantity to add/update |

#### Sample Response (200 OK)

```json
{
  "id": 1,
  "branchId": 1,
  "productId": 101,
  "quantity": 65,
  "minimumStock": 10
}
```

---

## Complete Workflow Example

### Step 1: Register a new user

```bash
curl -X POST http://localhost:8081/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "alice_smith",
    "email": "alice@smartops.com",
    "password": "Alice@12345",
    "role": "MANAGER",
    "branchId": "2"
  }'
```

### Step 2: Login to get token

```bash
curl -X POST "http://localhost:8081/api/auth/login?username=alice_smith&password=Alice@12345" \
  -H "Content-Type: application/json"
```

### Step 3: Create inventory record

```bash
curl -X POST http://localhost:8083/api/inventory \
  -H "Content-Type: application/json" \
  -d '{
    "branchId": 2,
    "productId": 201,
    "quantity": 100,
    "minimumStock": 20
  }'
```

### Step 4: View all inventory

```bash
curl -X GET http://localhost:8083/api/inventory \
  -H "Content-Type: application/json"
```

### Step 5: Update stock after sale

```bash
curl -X POST "http://localhost:8083/api/inventory/update?branchId=2&productId=201&qty=85" \
  -H "Content-Type: application/json"
```

---

## Importing into Postman

1. Download the `POSTMAN_COLLECTION.json` file
2. Open Postman
3. Click **Import** (top-left)
4. Select **Upload Files**
5. Choose the downloaded JSON file
6. All requests will be imported with proper organization

---

## Common HTTP Status Codes

| Code | Meaning | Description |
|------|---------|-------------|
| 200 | OK | Request successful |
| 201 | Created | Resource created successfully |
| 400 | Bad Request | Invalid request format |
| 401 | Unauthorized | Authentication required |
| 404 | Not Found | Resource not found |
| 500 | Server Error | Internal server error |

---

## Tips

- Always include `Content-Type: application/json` header
- Use query parameters for GET/POST endpoints
- Store JWT token from login for authenticated requests
- Check service ports if running locally
- Use appropriate HTTP methods (POST for create, GET for retrieve, PUT/POST for update)

