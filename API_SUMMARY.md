# Smart-S2R Backend - API Documentation Summary

**Project:** Smart-S2R (Suppliers-to-Retailers Platform)  
**Version:** 0.0.1-SNAPSHOT  
**Date Generated:** November 16, 2025  
**API Version:** 1.0.0

---

## 📚 Documentation Files

This backend provides comprehensive API documentation in multiple formats:

### 1. **API_DOCUMENTATION.md** - Complete API Reference
   - Detailed endpoint documentation with examples
   - Request/response schemas for all endpoints
   - Authentication and error handling guidelines
   - Example usage workflows for retailers and suppliers
   - **Best for:** Frontend developers who need complete details

### 2. **API_ENDPOINTS.json** - Structured Endpoint Listing
   - All endpoints in JSON format
   - Easy to parse and integrate with tools
   - Organized by resource type
   - Includes request/response structures
   - **Best for:** API documentation tools and integration

### 3. **QUICK_REFERENCE.md** - Quick Start Guide
   - One-page cheat sheet with all endpoints
   - Common curl examples
   - Key data models and enums
   - Quick start workflow
   - **Best for:** Quick lookup and quick integration

---

## 🎯 API Overview

### Total Endpoints: 26

#### By Category:
- **Authentication:** 3 endpoints
- **Retailer Profiles:** 8 endpoints
- **Supplier Profiles:** 2 endpoints
- **Orders:** 5 endpoints
- **Ratings:** 2 endpoints

#### By Authentication:
- **Public (No Auth Required):** 18 endpoints
- **Protected (Auth Required):** 8 endpoints

---

## 🔑 Authentication Details

**Type:** JWT (JSON Web Token) Bearer Authentication  
**Header Format:** `Authorization: Bearer {token}`

**Token Contents:**
- Subject: User's phone number
- Role: RETAILER, SUPPLIER, or ADMIN
- User ID
- Issued at & Expiration timestamps

**Token Expiry:**
- Access Token: 24 hours (86400000 ms)
- Refresh Token: 30 days (2592000000 ms)

---

## 📋 Complete Endpoint Listing

### Authentication (3 endpoints)
```
POST   /api/v1/auth/register    - Register new user
POST   /api/v1/auth/login       - Login user
POST   /api/v1/auth/refresh     - Refresh access token
```

### Retailer Profiles (8 endpoints)
```
POST   /api/v1/retailers                               - Create profile
GET    /api/v1/retailers/{id}                          - Get by ID
GET    /api/v1/retailers/user/{userId}                 - Get by user ID
PUT    /api/v1/retailers/{id}                          - Update profile
DELETE /api/v1/retailers/{id}                          - Delete profile
POST   /api/v1/retailers/nearby-suppliers              - Find suppliers (coords)
GET    /api/v1/retailers/{retailerId}/nearby-suppliers - Find suppliers (profile)
GET    /api/v1/retailers/me/nearby-suppliers           - Find suppliers (me)
```

### Supplier Profiles (2 endpoints)
```
POST   /api/v1/suppliers        - Create profile
GET    /api/v1/suppliers/nearby - Get nearby suppliers
```

### Orders (5 endpoints)
```
POST   /api/v1/orders                  - Create order
GET    /api/v1/orders/{id}             - Get order details
GET    /api/v1/orders/retailer/{id}    - Get retailer's orders
GET    /api/v1/orders/supplier/{id}    - Get supplier's orders
PATCH  /api/v1/orders/{id}/status      - Update order status
```

### Ratings (2 endpoints)
```
POST   /api/v1/ratings                        - Submit rating
GET    /api/v1/ratings/supplier/{id}/stats   - Get supplier stats
```

---

## 🎨 Key Features

### 1. Location-Based Supplier Discovery
- Find suppliers within a specific radius (in km)
- Three ways to search:
  - By explicit coordinates
  - By retailer's stored profile location
  - By authenticated user's location
- Results automatically sorted by distance (closest first)
- Uses Haversine formula for accurate calculations

### 2. Order Management
- Create orders with specific suppliers
- Track order status through workflow (PENDING → IN_PROGRESS → DELIVERED)
- Get order history for retailers or suppliers
- All orders timestamped

### 3. Supplier Rating System
- Retailers can rate suppliers (1-5 score)
- View supplier rating statistics (average & count)
- Comments available for ratings

### 4. User Role System
- **RETAILER:** Can browse suppliers, create orders, rate suppliers
- **SUPPLIER:** Can receive orders, update delivery status
- **ADMIN:** Administrative access (reserved for future features)

---

## 🗄️ Database Entities

### User
- ID, Full Name, Phone (unique), Email, Password Hash, Role, Created At

### RetailerProfile
- ID, User (1:1), Shop Name, Address, Latitude, Longitude, Description

### SupplierProfile
- ID, User (1:1), Business Name, Address, Latitude, Longitude, Description

### Order
- ID, Retailer (1:N), Supplier (1:N), Status, Total Amount, Notes, Timestamps

### Rating
- ID, Retailer (N:1), Supplier (N:1), Score (1-5), Comment, Created At

### RefreshToken
- ID, User (1:1), Token, Expires At

---

## 🛡️ Security Features

✅ JWT token-based authentication  
✅ Password hashing with BCrypt  
✅ Phone number uniqueness validation  
✅ Role-based access control  
✅ Secure token refresh mechanism  
✅ CSRF protection disabled (stateless API)  

---

## 📊 Status Codes

| Code | Meaning | Example |
|------|---------|---------|
| 200 | OK | Successful request |
| 201 | Created | Resource created (usually returns with 200 instead) |
| 204 | No Content | Successful deletion |
| 400 | Bad Request | Validation error, invalid parameters |
| 401 | Unauthorized | Missing or invalid JWT token |
| 404 | Not Found | Resource not found (user, order, profile, etc.) |
| 500 | Server Error | Internal server error |

---

## 🔄 Typical User Flows

### Retailer Flow
1. Register → 2. Create Profile → 3. Browse Suppliers → 4. Create Order → 5. Track Order → 6. Rate Supplier

### Supplier Flow
1. Register → 2. Create Profile → 3. View Orders → 4. Update Order Status → 5. View Ratings

---

## 🧪 Testing the API

### Using cURL
```bash
# Register
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{"fullName":"John","phone":"+254712345678","email":"john@example.com","password":"pass","role":"RETAILER"}'

# Login
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"phone":"+254712345678","password":"pass"}'

# Find nearby suppliers
curl -X POST http://localhost:8080/api/v1/retailers/nearby-suppliers \
  -H "Content-Type: application/json" \
  -d '{"latitude":-1.2833,"longitude":36.8167,"radiusKm":10}'
```

### Using Postman
1. Import the API_ENDPOINTS.json file
2. Set up a Bearer token authentication variable
3. Test each endpoint with example data

### Using Thunder Client / VS Code REST Client
```rest
### Register User
POST http://localhost:8080/api/v1/auth/register
Content-Type: application/json

{
  "fullName": "John Doe",
  "phone": "+254712345678",
  "email": "john@example.com",
  "password": "password123",
  "role": "RETAILER"
}

### Login
POST http://localhost:8080/api/v1/auth/login
Content-Type: application/json

{
  "phone": "+254712345678",
  "password": "password123"
}
```

---

## ✅ What's Implemented

- ✅ User registration with role assignment
- ✅ JWT-based authentication
- ✅ Retailer profile management
- ✅ Supplier profile management
- ✅ Location-based supplier discovery (with Haversine formula)
- ✅ Order creation and tracking
- ✅ Order status updates
- ✅ Supplier rating system
- ✅ Rating statistics

---

## 🚀 What's Next (Future Enhancements)

- [ ] Admin console
- [ ] Advanced analytics dashboard
- [ ] Real-time order tracking (WebSocket)
- [ ] Inventory management
- [ ] Payment integration
- [ ] SMS/Email notifications
- [ ] Mobile app push notifications
- [ ] Delivery time estimation
- [ ] Multi-language support

---

## 🔗 Related Documentation

- **API_DOCUMENTATION.md** - Full technical documentation
- **API_ENDPOINTS.json** - Structured endpoint data
- **QUICK_REFERENCE.md** - One-page quick guide
- **pom.xml** - Maven dependencies and build configuration
- **application.properties** - Application configuration

---

## 📞 Support & Contact

For questions or issues:
1. Check the API_DOCUMENTATION.md for detailed explanations
2. Review QUICK_REFERENCE.md for common examples
3. Contact the backend development team

---

## 🏗️ Technical Stack

- **Language:** Java 25
- **Framework:** Spring Boot 3.5.7
- **Database:** PostgreSQL 18.1
- **Security:** Spring Security + JWT (JJWT 0.11.5)
- **Build Tool:** Maven
- **ORM:** Hibernate/JPA
- **Validation:** Jakarta Validation

---

## 📝 Version History

| Version | Date | Changes |
|---------|------|---------|
| 1.0.0 | 2025-11-16 | Initial API release with all core features |
| 0.0.1-SNAPSHOT | 2025-11-16 | Development build |

---

**Last Updated:** November 16, 2025  
**Status:** Ready for Frontend Integration  
**Backend Running:** http://localhost:8080
