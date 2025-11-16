# Smart-S2R Backend - Quick Reference Guide

**Base URL:** `http://localhost:8080/api/v1`  
**Authentication:** JWT Bearer Token (required for most endpoints marked with 🔐)

---

## 📋 Endpoint Summary

### 🔑 Authentication (NO AUTH REQUIRED)
| Endpoint | Method | Purpose |
|----------|--------|---------|
| `/auth/register` | POST | Register new user |
| `/auth/login` | POST | Login user |
| `/auth/refresh` | POST | Refresh access token |

### 🏪 Retailer Profiles (MOSTLY NO AUTH for GET, AUTH for CREATE/UPDATE/DELETE)
| Endpoint | Method | Auth | Purpose |
|----------|--------|------|---------|
| `/retailers` | POST | 🔐 | Create profile |
| `/retailers/{id}` | GET | ❌ | Get by profile ID |
| `/retailers/user/{userId}` | GET | ❌ | Get by user ID |
| `/retailers/{id}` | PUT | 🔐 | Update profile |
| `/retailers/{id}` | DELETE | 🔐 | Delete profile |
| `/retailers/nearby-suppliers` | POST | ❌ | Find suppliers by coords |
| `/retailers/{retailerId}/nearby-suppliers` | GET | ❌ | Find suppliers from profile |
| `/retailers/me/nearby-suppliers` | GET | 🔐 | Find suppliers for me |

### 🏭 Supplier Profiles
| Endpoint | Method | Auth | Purpose |
|----------|--------|------|---------|
| `/suppliers` | POST | ❌ | Create profile |
| `/suppliers/nearby` | GET | ❌ | Find nearby suppliers |

### 📦 Orders
| Endpoint | Method | Auth | Purpose |
|----------|--------|------|---------|
| `/orders` | POST | 🔐 | Create order |
| `/orders/{id}` | GET | ❌ | Get order details |
| `/orders/retailer/{id}` | GET | ❌ | Get retailer's orders |
| `/orders/supplier/{id}` | GET | ❌ | Get supplier's orders |
| `/orders/{id}/status` | PATCH | 🔐 | Update order status |

### ⭐ Ratings
| Endpoint | Method | Auth | Purpose |
|----------|--------|------|---------|
| `/ratings` | POST | 🔐 | Submit rating |
| `/ratings/supplier/{id}/stats` | GET | ❌ | Get supplier rating stats |

---

## 🔐 Authentication Header

```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIrMjU0NzEyMzQ1Njc4Iiwicm9sZSI6IlJFVEFJTEVSIiwiZXhwIjoxNjMyNDU5MjAwfQ.signature
```

---

## 🎯 Common Request/Response Examples

### Register User
```bash
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "John Doe",
    "phone": "+254712345678",
    "email": "john@example.com",
    "password": "password123",
    "role": "RETAILER"
  }'
```

### Login User
```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "phone": "+254712345678",
    "password": "password123"
  }'
```

### Create Retailer Profile
```bash
curl -X POST http://localhost:8080/api/v1/retailers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {token}" \
  -d '{
    "shopName": "My Mini Mart",
    "address": "123 Main Street, Nairobi",
    "latitude": -1.2833,
    "longitude": 36.8167,
    "description": "Quality retail store"
  }'
```

### Find Nearby Suppliers (via Coordinates)
```bash
curl -X POST http://localhost:8080/api/v1/retailers/nearby-suppliers \
  -H "Content-Type: application/json" \
  -d '{
    "latitude": -1.2833,
    "longitude": 36.8167,
    "radiusKm": 10
  }'
```

### Find Nearby Suppliers (for Authenticated User)
```bash
curl -X GET "http://localhost:8080/api/v1/retailers/me/nearby-suppliers?radiusKm=10" \
  -H "Authorization: Bearer {token}"
```

### Create Order
```bash
curl -X POST http://localhost:8080/api/v1/orders \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {token}" \
  -d '{
    "supplierId": 5,
    "totalAmount": 25000.50,
    "notes": "Please deliver by noon"
  }'
```

### Update Order Status
```bash
curl -X PATCH http://localhost:8080/api/v1/orders/1/status \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {token}" \
  -d '{
    "status": "IN_PROGRESS"
  }'
```

### Submit Rating
```bash
curl -X POST http://localhost:8080/api/v1/ratings \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {token}" \
  -d '{
    "supplierId": 5,
    "score": 4,
    "comment": "Excellent service!"
  }'
```

### Get Supplier Rating Stats
```bash
curl -X GET http://localhost:8080/api/v1/ratings/supplier/5/stats
```

---

## 📊 Data Models

### Role
```
RETAILER    - Retail business
SUPPLIER    - Wholesale/supplier business
ADMIN       - Administrator
```

### Order Status
```
PENDING     - Order placed, awaiting processing
IN_PROGRESS - Supplier is preparing/shipping order
DELIVERED   - Order delivered to retailer
CANCELLED   - Order was cancelled
```

### Rating Score
```
1 - Very poor
2 - Poor
3 - Average
4 - Good
5 - Excellent
```

---

## ⚠️ Important Notes

1. **Phone Number**: Used as unique identifier for login. Should be in E.164 format (e.g., +254712345678)
2. **Location Data**: Coordinates should be in decimal degrees
3. **Distance Calculation**: Uses Haversine formula, results in kilometers
4. **Token Expiry**: Access tokens expire after 24 hours, use refresh token to get new token
5. **Nearby Suppliers**: Returns results sorted by distance (closest first)

---

## 🚀 Quick Start for Frontend Developer

1. **Register a retailer:**
   - Call `/auth/register` with role `RETAILER`
   - Save the returned `token` and `refreshToken`

2. **Create retailer profile:**
   - Call `/retailers` with location data (latitude, longitude)
   - This enables location-based features

3. **Find nearby suppliers:**
   - Call `/retailers/me/nearby-suppliers?radiusKm=10` (authenticated)
   - Returns list of suppliers sorted by distance

4. **Create an order:**
   - Call `/orders` with `supplierId` and `totalAmount`
   - Order starts with status `PENDING`

5. **Track order status:**
   - Poll `/orders/{orderId}` or `/orders/retailer/{retailerId}`
   - Status changes: PENDING → IN_PROGRESS → DELIVERED

6. **Rate supplier:**
   - Call `/ratings` after order delivery
   - Score from 1-5 with optional comment

---

## 🔧 Error Handling

### All errors return this format:
```json
{
  "error": "Error type or message",
  "message": "Detailed error description"
}
```

### Common error scenarios:
- `400 Bad Request` - Validation error, check request parameters
- `401 Unauthorized` - Missing/invalid token, login again
- `404 Not Found` - Resource doesn't exist, check ID
- `500 Internal Server Error` - Server issue, try again

---

**Generated:** November 16, 2025  
**API Version:** 1.0.0  
**Backend Version:** smart-s2r-backend 0.0.1-SNAPSHOT
