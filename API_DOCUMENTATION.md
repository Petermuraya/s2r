# Smart-S2R Backend API Documentation

**Version:** 1.0.0  
**Base URL:** `http://localhost:8080`  
**API Prefix:** `/api/v1`

---

## 📋 Table of Contents

1. [Authentication & Authorization](#authentication--authorization)
2. [Retailer Profile Management](#retailer-profile-management)
3. [Supplier Profile Management](#supplier-profile-management)
4. [Order Management](#order-management)
5. [Ratings System](#ratings-system)
6. [Security & Error Handling](#security--error-handling)

---

## 🔐 Authentication & Authorization

### 1. Register User

**Endpoint:** `POST /api/v1/auth/register`

**Description:** Register a new user (Retailer, Supplier, or Admin)

**Request Body:**
```json
{
  "fullName": "John Doe",
  "phone": "+254712345678",
  "email": "john@example.com",
  "password": "securePassword123",
  "role": "RETAILER"  // or "SUPPLIER", "ADMIN"
}
```

**Response:** `200 OK`
```json
{
  "userId": 1,
  "fullName": "John Doe",
  "phone": "+254712345678",
  "role": "RETAILER",
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiJ9..."
}
```

**Error Responses:**
- `400 Bad Request` - Phone already registered
- `400 Bad Request` - Validation error

**Role Options:**
- `RETAILER` - Retail business user
- `SUPPLIER` - Supplier/Wholesale business user
- `ADMIN` - Administrator

---

### 2. Login User

**Endpoint:** `POST /api/v1/auth/login`

**Description:** Authenticate user and receive JWT token

**Request Body:**
```json
{
  "phone": "+254712345678",
  "password": "securePassword123"
}
```

**Response:** `200 OK`
```json
{
  "userId": 1,
  "fullName": "John Doe",
  "phone": "+254712345678",
  "role": "RETAILER",
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiJ9..."
}
```

**Error Responses:**
- `400 Bad Request` - User not found
- `400 Bad Request` - Invalid password
- `400 Bad Request` - Validation error

---

### 3. Refresh Access Token

**Endpoint:** `POST /api/v1/auth/refresh`

**Description:** Get a new access token using refresh token

**Request Body:**
```json
{
  "refreshToken": "eyJhbGciOiJIUzI1NiJ9..."
}
```

**Response:** `200 OK`
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

**Error Responses:**
- `400 Bad Request` - Refresh token required
- `400 Bad Request` - Refresh token expired
- `400 Bad Request` - Invalid refresh token

---

## 🏪 Retailer Profile Management

### 1. Create Retailer Profile

**Endpoint:** `POST /api/v1/retailers`

**Authentication:** Required (JWT Token)

**Description:** Create a retailer profile for authenticated user

**Request Body:**
```json
{
  "shopName": "My Mini Mart",
  "address": "123 Main Street, Nairobi",
  "latitude": -1.2833,
  "longitude": 36.8167,
  "description": "Quality retail store with variety of products"
}
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "userId": 1,
  "shopName": "My Mini Mart",
  "address": "123 Main Street, Nairobi",
  "latitude": -1.2833,
  "longitude": 36.8167,
  "description": "Quality retail store with variety of products"
}
```

**Error Responses:**
- `401 Unauthorized` - Token missing or invalid
- `400 Bad Request` - Validation error

---

### 2. Get Retailer Profile by ID

**Endpoint:** `GET /api/v1/retailers/{id}`

**Description:** Retrieve retailer profile details by profile ID

**Response:** `200 OK`
```json
{
  "id": 1,
  "userId": 1,
  "shopName": "My Mini Mart",
  "address": "123 Main Street, Nairobi",
  "latitude": -1.2833,
  "longitude": 36.8167,
  "description": "Quality retail store with variety of products"
}
```

**Error Responses:**
- `404 Not Found` - Profile not found

---

### 3. Get Retailer Profile by User ID

**Endpoint:** `GET /api/v1/retailers/user/{userId}`

**Description:** Retrieve retailer profile by user ID

**Response:** `200 OK`
```json
{
  "id": 1,
  "userId": 1,
  "shopName": "My Mini Mart",
  "address": "123 Main Street, Nairobi",
  "latitude": -1.2833,
  "longitude": 36.8167,
  "description": "Quality retail store with variety of products"
}
```

**Error Responses:**
- `404 Not Found` - Profile not found

---

### 4. Update Retailer Profile

**Endpoint:** `PUT /api/v1/retailers/{id}`

**Authentication:** Required (JWT Token)

**Description:** Update existing retailer profile

**Request Body:**
```json
{
  "shopName": "Updated Shop Name",
  "address": "456 New Avenue, Nairobi",
  "latitude": -1.2900,
  "longitude": 36.8200,
  "description": "Updated description"
}
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "userId": 1,
  "shopName": "Updated Shop Name",
  "address": "456 New Avenue, Nairobi",
  "latitude": -1.2900,
  "longitude": 36.8200,
  "description": "Updated description"
}
```

**Error Responses:**
- `401 Unauthorized` - Token missing or invalid
- `400 Bad Request` - Validation error

---

### 5. Delete Retailer Profile

**Endpoint:** `DELETE /api/v1/retailers/{id}`

**Authentication:** Required (JWT Token)

**Description:** Delete retailer profile

**Response:** `204 No Content`

**Error Responses:**
- `401 Unauthorized` - Token missing or invalid

---

### 6. Find Nearby Suppliers (from Coordinates)

**Endpoint:** `POST /api/v1/retailers/nearby-suppliers`

**Description:** Find suppliers near specified coordinates

**Request Body:**
```json
{
  "latitude": -1.2833,
  "longitude": 36.8167,
  "radiusKm": 10.0
}
```

**Response:** `200 OK`
```json
[
  {
    "supplierId": 5,
    "userId": 5,
    "businessName": "Premium Wholesale Co.",
    "address": "456 Industrial Zone, Nairobi",
    "phone": "+254712345680",
    "email": "supplier@wholesale.com",
    "latitude": -1.2850,
    "longitude": 36.8180,
    "description": "Bulk wholesale supplier",
    "distanceKm": 2.5
  },
  {
    "supplierId": 6,
    "userId": 6,
    "businessName": "Quality Supplies Ltd",
    "address": "789 Trade Center, Nairobi",
    "phone": "+254712345681",
    "email": "quality@supplies.com",
    "latitude": -1.2900,
    "longitude": 36.8200,
    "description": "Quality supplier",
    "distanceKm": 5.2
  }
]
```

**Error Responses:**
- `400 Bad Request` - Invalid parameters

---

### 7. Find Nearby Suppliers (from Profile Location)

**Endpoint:** `GET /api/v1/retailers/{retailerId}/nearby-suppliers`

**Query Parameters:**
- `radiusKm` (optional, default: 5.0) - Search radius in kilometers

**Description:** Find suppliers near retailer's stored location

**Response:** `200 OK`
```json
[
  {
    "supplierId": 5,
    "userId": 5,
    "businessName": "Premium Wholesale Co.",
    "address": "456 Industrial Zone, Nairobi",
    "phone": "+254712345680",
    "email": "supplier@wholesale.com",
    "latitude": -1.2850,
    "longitude": 36.8180,
    "description": "Bulk wholesale supplier",
    "distanceKm": 2.5
  }
]
```

**Error Responses:**
- `404 Not Found` - Profile not found
- `400 Bad Request` - Retailer location not set

---

### 8. Find Nearby Suppliers (for Authenticated User)

**Endpoint:** `GET /api/v1/retailers/me/nearby-suppliers`

**Authentication:** Required (JWT Token)

**Query Parameters:**
- `radiusKm` (optional, default: 5.0) - Search radius in kilometers

**Description:** Find suppliers near authenticated retailer's location

**Response:** `200 OK`
```json
[
  {
    "supplierId": 5,
    "userId": 5,
    "businessName": "Premium Wholesale Co.",
    "address": "456 Industrial Zone, Nairobi",
    "phone": "+254712345680",
    "email": "supplier@wholesale.com",
    "latitude": -1.2850,
    "longitude": 36.8180,
    "description": "Bulk wholesale supplier",
    "distanceKm": 2.5
  }
]
```

**Error Responses:**
- `401 Unauthorized` - Token missing or invalid
- `400 Bad Request` - Profile not found or location not set

---

## 🏭 Supplier Profile Management

### 1. Create Supplier Profile

**Endpoint:** `POST /api/v1/suppliers`

**Description:** Create a supplier profile

**Request Body:**
```json
{
  "userId": 5,
  "businessName": "Premium Wholesale Co.",
  "address": "456 Industrial Zone, Nairobi",
  "latitude": -1.2850,
  "longitude": 36.8180,
  "description": "Bulk wholesale supplier with quality products"
}
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "userId": 5,
  "businessName": "Premium Wholesale Co.",
  "address": "456 Industrial Zone, Nairobi",
  "latitude": -1.2850,
  "longitude": 36.8180,
  "description": "Bulk wholesale supplier with quality products"
}
```

**Error Responses:**
- `400 Bad Request` - User not found
- `400 Bad Request` - User is not a supplier role

---

### 2. Get Nearby Suppliers (Legacy Endpoint)

**Endpoint:** `GET /api/v1/suppliers/nearby`

**Query Parameters:**
- `lat` (required) - Latitude coordinate
- `lon` (required) - Longitude coordinate
- `radiusKm` (optional, default: 10) - Search radius in kilometers

**Description:** Find suppliers near specified coordinates (returns basic supplier info)

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "userId": 5,
    "businessName": "Premium Wholesale Co.",
    "address": "456 Industrial Zone, Nairobi",
    "latitude": -1.2850,
    "longitude": 36.8180,
    "description": "Bulk wholesale supplier"
  }
]
```

**Error Responses:**
- `400 Bad Request` - Invalid parameters

---

## 📦 Order Management

### 1. Create Order

**Endpoint:** `POST /api/v1/orders`

**Authentication:** Required (JWT Token)

**Description:** Create a new order (Retailer creates order with Supplier)

**Request Body:**
```json
{
  "supplierId": 5,
  "totalAmount": 25000.50,
  "notes": "Please deliver by noon. Include invoice."
}
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "retailerId": 1,
  "supplierId": 5,
  "status": "PENDING",
  "totalAmount": 25000.50,
  "notes": "Please deliver by noon. Include invoice.",
  "createdAt": "2025-11-16T10:30:00+03:00",
  "updatedAt": null,
  "deliveredAt": null
}
```

**Order Status Values:** `PENDING`, `IN_PROGRESS`, `DELIVERED`, `CANCELLED`

**Error Responses:**
- `401 Unauthorized` - Token missing or invalid
- `400 Bad Request` - Supplier ID required or invalid

---

### 2. Get Retailer's Orders

**Endpoint:** `GET /api/v1/orders/retailer/{id}`

**Description:** Retrieve all orders for a specific retailer

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "retailerId": 1,
    "supplierId": 5,
    "status": "PENDING",
    "totalAmount": 25000.50,
    "notes": "Please deliver by noon.",
    "createdAt": "2025-11-16T10:30:00+03:00",
    "updatedAt": null,
    "deliveredAt": null
  },
  {
    "id": 2,
    "retailerId": 1,
    "supplierId": 6,
    "status": "DELIVERED",
    "totalAmount": 15000.00,
    "notes": "Standard order",
    "createdAt": "2025-11-15T14:20:00+03:00",
    "updatedAt": "2025-11-15T16:00:00+03:00",
    "deliveredAt": "2025-11-15T17:00:00+03:00"
  }
]
```

---

### 3. Get Supplier's Orders

**Endpoint:** `GET /api/v1/orders/supplier/{id}`

**Description:** Retrieve all orders received by a specific supplier

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "retailerId": 1,
    "supplierId": 5,
    "status": "PENDING",
    "totalAmount": 25000.50,
    "notes": "Please deliver by noon.",
    "createdAt": "2025-11-16T10:30:00+03:00",
    "updatedAt": null,
    "deliveredAt": null
  }
]
```

---

### 4. Get Order Details

**Endpoint:** `GET /api/v1/orders/{id}`

**Description:** Retrieve specific order details

**Response:** `200 OK`
```json
{
  "id": 1,
  "retailerId": 1,
  "supplierId": 5,
  "status": "PENDING",
  "totalAmount": 25000.50,
  "notes": "Please deliver by noon.",
  "createdAt": "2025-11-16T10:30:00+03:00",
  "updatedAt": null,
  "deliveredAt": null
}
```

**Error Responses:**
- `404 Not Found` - Order not found

---

### 5. Update Order Status

**Endpoint:** `PATCH /api/v1/orders/{id}/status`

**Authentication:** Required (JWT Token)

**Description:** Update the status of an order (used by supplier to update delivery status)

**Request Body:**
```json
{
  "status": "IN_PROGRESS"
}
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "retailerId": 1,
  "supplierId": 5,
  "status": "IN_PROGRESS",
  "totalAmount": 25000.50,
  "notes": "Please deliver by noon.",
  "createdAt": "2025-11-16T10:30:00+03:00",
  "updatedAt": "2025-11-16T11:00:00+03:00",
  "deliveredAt": null
}
```

**Valid Status Transitions:**
- `PENDING` → `IN_PROGRESS`
- `IN_PROGRESS` → `DELIVERED`
- Any status → `CANCELLED`

**Error Responses:**
- `401 Unauthorized` - Token missing or invalid
- `400 Bad Request` - Invalid status

---

## ⭐ Ratings System

### 1. Submit Rating

**Endpoint:** `POST /api/v1/ratings`

**Authentication:** Required (JWT Token)

**Description:** Submit a rating for a supplier (Retailer rates Supplier)

**Request Body:**
```json
{
  "supplierId": 5,
  "score": 4,
  "comment": "Excellent service and quality products. Fast delivery!"
}
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "retailerId": 1,
  "supplierId": 5,
  "score": 4,
  "comment": "Excellent service and quality products. Fast delivery!",
  "createdAt": "2025-11-16T10:30:00+03:00"
}
```

**Rating Score Range:** 1 - 5 (must be integer)

**Error Responses:**
- `401 Unauthorized` - Token missing or invalid
- `400 Bad Request` - Invalid score (not between 1-5)
- `400 Bad Request` - Supplier ID required

---

### 2. Get Supplier Rating Statistics

**Endpoint:** `GET /api/v1/ratings/supplier/{id}/stats`

**Description:** Retrieve average rating and total rating count for a supplier

**Response:** `200 OK`
```json
{
  "average": 4.3,
  "count": 15
}
```

---

## 🔒 Security & Error Handling

### Authentication

All endpoints marked with **"Authentication: Required"** require a JWT token in the `Authorization` header:

```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

### JWT Token Structure

The JWT token contains:
- `subject`: User's phone number
- `role`: User's role (RETAILER, SUPPLIER, ADMIN)
- `id`: User's ID
- `iat`: Issued at timestamp
- `exp`: Expiration timestamp (1 day by default)

### Common Error Responses

**400 Bad Request**
```json
{
  "error": "Invalid request parameters",
  "message": "Validation failed"
}
```

**401 Unauthorized**
```json
{
  "error": "Unauthorized",
  "message": "Invalid or missing JWT token"
}
```

**404 Not Found**
```json
{
  "error": "Resource not found",
  "message": "The requested resource could not be found"
}
```

**500 Internal Server Error**
```json
{
  "error": "Internal server error",
  "message": "An unexpected error occurred"
}
```

### Response Status Codes

| Code | Meaning |
|------|---------|
| 200 | OK - Request successful |
| 201 | Created - Resource created successfully |
| 204 | No Content - Request successful, no content to return |
| 400 | Bad Request - Invalid parameters or validation error |
| 401 | Unauthorized - Missing or invalid authentication |
| 404 | Not Found - Resource not found |
| 500 | Internal Server Error - Server error |

---

## 📝 Data Types & Enums

### Role Enum
```
RETAILER
SUPPLIER
ADMIN
```

### OrderStatus Enum
```
PENDING
IN_PROGRESS
DELIVERED
CANCELLED
```

### Location Data
- **Latitude/Longitude:** Decimal degrees (e.g., -1.2833, 36.8167)
- **Radius:** Kilometers (e.g., 5.0, 10.0)

---

## 🧪 Example Usage Flow

### Retailer Workflow

1. **Register as Retailer**
   ```bash
   POST /api/v1/auth/register
   {
     "fullName": "John Retail",
     "phone": "+254712345678",
     "email": "john@retail.com",
     "password": "securePassword",
     "role": "RETAILER"
   }
   ```

2. **Create Retailer Profile**
   ```bash
   POST /api/v1/retailers
   Authorization: Bearer {token}
   {
     "shopName": "City Shop",
     "address": "123 Main St",
     "latitude": -1.2833,
     "longitude": 36.8167,
     "description": "Retail store"
   }
   ```

3. **Find Nearby Suppliers**
   ```bash
   GET /api/v1/retailers/me/nearby-suppliers?radiusKm=10
   Authorization: Bearer {token}
   ```

4. **Create Order**
   ```bash
   POST /api/v1/orders
   Authorization: Bearer {token}
   {
     "supplierId": 5,
     "totalAmount": 25000.00,
     "notes": "Urgent delivery needed"
   }
   ```

5. **Rate Supplier**
   ```bash
   POST /api/v1/ratings
   Authorization: Bearer {token}
   {
     "supplierId": 5,
     "score": 5,
     "comment": "Excellent service!"
   }
   ```

### Supplier Workflow

1. **Register as Supplier**
   ```bash
   POST /api/v1/auth/register
   {
     "fullName": "Jane Supplier",
     "phone": "+254712345679",
     "email": "jane@supplier.com",
     "password": "securePassword",
     "role": "SUPPLIER"
   }
   ```

2. **Create Supplier Profile**
   ```bash
   POST /api/v1/suppliers
   {
     "userId": 5,
     "businessName": "Wholesale Hub",
     "address": "456 Industrial Ave",
     "latitude": -1.2850,
     "longitude": 36.8180,
     "description": "Wholesale supplier"
   }
   ```

3. **View Orders**
   ```bash
   GET /api/v1/orders/supplier/5
   ```

4. **Update Order Status**
   ```bash
   PATCH /api/v1/orders/1/status
   Authorization: Bearer {token}
   {
     "status": "IN_PROGRESS"
   }
   ```

---

## 📞 Support

For issues or questions about the API, please contact the backend team.

**Last Updated:** November 16, 2025  
**Backend Version:** 0.0.1-SNAPSHOT
