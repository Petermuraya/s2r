# 🎯 Frontend Developer Quick Start

**Backend:** Smart-S2R API  
**Version:** 1.0.0  
**Status:** ✅ Ready to Integrate

---

## 📚 Documentation You Have

```
📦 smart-s2r-backend/
├── 📄 DOCUMENTATION_INDEX.md ⭐ START HERE
├── 📄 API_SUMMARY.md (2-3 min read)
├── 📄 QUICK_REFERENCE.md (quick lookup)
├── 📄 API_DOCUMENTATION.md (comprehensive)
├── 📄 DTO_REFERENCE.md (data models)
└── 📄 API_ENDPOINTS.json (for tools)
```

---

## 🚀 5-Minute Quick Start

### Step 1: Register a User
```bash
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "John Retailer",
    "phone": "+254712345678",
    "email": "john@retail.com",
    "password": "password123",
    "role": "RETAILER"
  }'
```

**Response:**
```json
{
  "userId": 1,
  "fullName": "John Retailer",
  "phone": "+254712345678",
  "role": "RETAILER",
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiJ9..."
}
```

**Save this token!** Use it for authenticated requests.

---

### Step 2: Create Retailer Profile
```bash
curl -X POST http://localhost:8080/api/v1/retailers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {YOUR_TOKEN_HERE}" \
  -d '{
    "shopName": "City Mini Mart",
    "address": "123 Main Street, Nairobi",
    "latitude": -1.2833,
    "longitude": 36.8167,
    "description": "Quality retail store"
  }'
```

**Response:**
```json
{
  "id": 1,
  "userId": 1,
  "shopName": "City Mini Mart",
  "address": "123 Main Street, Nairobi",
  "latitude": -1.2833,
  "longitude": 36.8167,
  "description": "Quality retail store"
}
```

---

### Step 3: Find Nearby Suppliers
```bash
curl -X POST http://localhost:8080/api/v1/retailers/nearby-suppliers \
  -H "Content-Type: application/json" \
  -d '{
    "latitude": -1.2833,
    "longitude": 36.8167,
    "radiusKm": 10
  }'
```

**Response:**
```json
[
  {
    "supplierId": 5,
    "userId": 5,
    "businessName": "Premium Wholesale",
    "address": "456 Industrial Zone",
    "phone": "+254712345680",
    "email": "supplier@wholesale.com",
    "latitude": -1.2850,
    "longitude": 36.8180,
    "description": "Bulk wholesale",
    "distanceKm": 2.5
  },
  {
    "supplierId": 6,
    "userId": 6,
    "businessName": "Quality Supplies",
    "address": "789 Trade Center",
    "phone": "+254712345681",
    "email": "quality@supplies.com",
    "latitude": -1.2900,
    "longitude": 36.8200,
    "description": "Quality supplier",
    "distanceKm": 5.2
  }
]
```

**Congratulations! You can now:**
- ✅ Register users
- ✅ Create profiles
- ✅ Find nearby suppliers

---

## 📖 Complete Endpoint List

### Public Endpoints (No Auth Required)

#### Authentication
- `POST /api/v1/auth/register` - Register user
- `POST /api/v1/auth/login` - Login user
- `POST /api/v1/auth/refresh` - Refresh token

#### Get Data
- `GET /api/v1/retailers/{id}` - Get retailer profile
- `GET /api/v1/retailers/user/{userId}` - Get by user ID
- `GET /api/v1/suppliers/nearby` - Find suppliers
- `GET /api/v1/orders/{id}` - Get order details
- `GET /api/v1/orders/retailer/{id}` - Get retailer orders
- `GET /api/v1/orders/supplier/{id}` - Get supplier orders
- `GET /api/v1/ratings/supplier/{id}/stats` - Get rating stats

#### Location-Based
- `POST /api/v1/retailers/nearby-suppliers` - Find suppliers from coords
- `GET /api/v1/retailers/{retailerId}/nearby-suppliers` - Find from profile

### Protected Endpoints (Auth Required 🔐)

#### Retailer Profile
- `POST /api/v1/retailers` - Create profile
- `PUT /api/v1/retailers/{id}` - Update profile
- `DELETE /api/v1/retailers/{id}` - Delete profile
- `GET /api/v1/retailers/me/nearby-suppliers` - My nearby suppliers

#### Supplier Profile
- `POST /api/v1/suppliers` - Create profile

#### Orders
- `POST /api/v1/orders` - Create order
- `PATCH /api/v1/orders/{id}/status` - Update status

#### Ratings
- `POST /api/v1/ratings` - Submit rating

---

## 🎨 Key Features Explained

### 1️⃣ Find Suppliers Near You
**3 Ways to Search:**

```
Option 1: By Coordinates (Public)
POST /api/v1/retailers/nearby-suppliers
{ "latitude": -1.2833, "longitude": 36.8167, "radiusKm": 10 }

Option 2: From Profile (Public)
GET /api/v1/retailers/123/nearby-suppliers?radiusKm=10

Option 3: My Location (Auth Required)
GET /api/v1/retailers/me/nearby-suppliers?radiusKm=10
```

✨ **All results sorted by distance (closest first)**

---

### 2️⃣ Order Management Workflow
```
1. Create Order
   POST /api/v1/orders
   { "supplierId": 5, "totalAmount": 25000 }
   
2. Track Status
   GET /api/v1/orders/retailer/1  (retailer view)
   GET /api/v1/orders/supplier/5  (supplier view)
   
3. Update Status (Supplier)
   PATCH /api/v1/orders/1/status
   { "status": "IN_PROGRESS" }
   
4. Statuses: PENDING → IN_PROGRESS → DELIVERED
```

---

### 3️⃣ Rating System
```
1. Rate Supplier (after delivery)
   POST /api/v1/ratings
   { "supplierId": 5, "score": 4, "comment": "Great!" }
   
2. View Supplier Ratings
   GET /api/v1/ratings/supplier/5/stats
   → Returns: { "average": 4.3, "count": 15 }
```

---

## 🔑 Authentication Guide

### Getting a Token
```bash
# Login
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"phone": "+254712345678", "password": "password123"}'

# Response contains "token"
```

### Using the Token
```bash
# Add to any protected request
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

### Token Expires?
```bash
# Refresh it
curl -X POST http://localhost:8080/api/v1/auth/refresh \
  -H "Content-Type: application/json" \
  -d '{"refreshToken": "eyJhbGciOiJIUzI1NiJ9..."}'
```

---

## 📊 Data Models at a Glance

### User Roles
```
RETAILER → Can browse suppliers, create orders, rate
SUPPLIER → Can receive orders, update delivery status
ADMIN    → Reserved for future features
```

### Order Status
```
PENDING      → Just created
IN_PROGRESS  → Being prepared/shipped
DELIVERED    → Received by retailer
CANCELLED    → Cancelled order
```

### Rating Score
```
1 ⭐ - Very Poor
2 ⭐ - Poor
3 ⭐ - Average
4 ⭐ - Good
5 ⭐ - Excellent
```

---

## ⚡ Tips & Tricks

### 1. Use Postman for Testing
- Import `API_ENDPOINTS.json` to Postman
- Set up Bearer token in Auth tab
- Test all endpoints with GUI

### 2. Save Your Token
```javascript
// JavaScript
const token = response.data.token;
localStorage.setItem('access_token', token);

// Use in requests
headers: {
  'Authorization': `Bearer ${localStorage.getItem('access_token')}`
}
```

### 3. Handle Token Refresh
```javascript
// When you get 401 Unauthorized
// 1. Use refresh token to get new token
// 2. Retry the failed request
// 3. Save new token
```

### 4. Validate Coordinates
```
Latitude:  -90 to +90  (e.g., -1.2833 for Nairobi)
Longitude: -180 to +180 (e.g., 36.8167 for Nairobi)
```

### 5. Always Include Content-Type
```bash
-H "Content-Type: application/json"
```

---

## 🐛 Common Issues & Solutions

### 401 Unauthorized
**Problem:** Token missing or expired
**Solution:** 
1. Check Authorization header is present
2. Use `/auth/refresh` to get new token
3. Login again if refresh fails

### 400 Bad Request
**Problem:** Invalid parameters
**Solution:**
1. Check JSON format is valid
2. Verify required fields are present
3. Check data types (lat/lon must be numbers)
4. See DTO_REFERENCE.md for validation rules

### 404 Not Found
**Problem:** Resource doesn't exist
**Solution:**
1. Verify ID is correct
2. Check resource was created first
3. Use correct endpoint path

### 500 Server Error
**Problem:** Server error
**Solution:**
1. Check if backend is running
2. Check database connection
3. Look at backend logs

---

## 📱 Example: Building a Retailer App

### Screen 1: Register
```
1. User fills form: name, phone, password
2. Call: POST /api/v1/auth/register
3. Save token from response
4. Go to next screen
```

### Screen 2: Create Shop Profile
```
1. User fills: shop name, location (map)
2. Call: POST /api/v1/retailers
3. Send latitude/longitude
4. Go to next screen
```

### Screen 3: Find Suppliers
```
1. Get user's location (latitude, longitude)
2. Call: GET /api/v1/retailers/me/nearby-suppliers
3. Show list sorted by distance
4. Show rating stars from stats
```

### Screen 4: Place Order
```
1. User taps supplier
2. User enters quantity/amount
3. Call: POST /api/v1/orders
4. Show order confirmation
```

### Screen 5: Track Order
```
1. Poll: GET /api/v1/orders/retailer/{id}
2. Show status updates
3. When delivered, show "Rate This Supplier" button
```

### Screen 6: Rate Supplier
```
1. User gives rating (1-5)
2. Optional: add comment
3. Call: POST /api/v1/ratings
4. Show "Thanks!" message
```

---

## 🎓 Where to Find What

| I Need to... | Check File | Section |
|---|---|---|
| Start building | DOCUMENTATION_INDEX.md | Quick Navigation |
| Understand endpoint | API_DOCUMENTATION.md | [endpoint name] |
| See curl example | QUICK_REFERENCE.md | Common Request/Response Examples |
| Know what fields | DTO_REFERENCE.md | Request DTOs |
| See response format | DTO_REFERENCE.md | Response DTOs |
| Find endpoint | QUICK_REFERENCE.md | Endpoint Summary |
| Setup Postman | API_ENDPOINTS.json | Import to Postman |

---

## ✅ Integration Checklist

- [ ] Read DOCUMENTATION_INDEX.md
- [ ] Understand authentication flow
- [ ] Test register & login endpoints
- [ ] Test create profile endpoint
- [ ] Test find suppliers endpoint
- [ ] Test create order endpoint
- [ ] Handle token refresh
- [ ] Setup error handling
- [ ] Test with Postman
- [ ] Ready for full integration!

---

## 🚀 You're Ready!

You now have:
- ✅ 26 working endpoints
- ✅ Complete documentation
- ✅ Quick examples
- ✅ Data structure reference
- ✅ Error handling guide

**Start building your frontend! 🎉**

---

**Next:** Open `DOCUMENTATION_INDEX.md` for complete navigation guide

**Questions?** Check the documentation files - they have detailed examples!

---

**Generated:** November 16, 2025  
**Backend Status:** ✅ Ready for Integration  
**Base URL:** http://localhost:8080/api/v1
