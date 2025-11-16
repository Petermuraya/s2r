# 📚 Smart-S2R Backend - Complete Documentation Index

**Version:** 1.0.0  
**Generated:** November 16, 2025  
**Status:** Ready for Frontend Integration  

---

## 📖 Documentation Files Overview

This backend includes comprehensive documentation designed for frontend developers. Choose the right document for your needs:

### 1. 🚀 **START HERE: API_SUMMARY.md**
   - **Purpose:** High-level overview of the entire API
   - **Contents:**
     - Project overview and statistics
     - Complete endpoint listing
     - Key features summary
     - Technical stack
     - Testing guidelines
   - **Best for:** Getting oriented with the backend
   - **Read time:** 5-10 minutes

### 2. 📋 **API_DOCUMENTATION.md** (MOST COMPREHENSIVE)
   - **Purpose:** Complete technical reference for all endpoints
   - **Contents:**
     - Detailed endpoint documentation
     - Request/response examples for each endpoint
     - Error handling guide
     - Authentication details
     - Example workflows (Retailer & Supplier)
   - **Best for:** Implementing frontend features
   - **Read time:** 30-45 minutes

### 3. ⚡ **QUICK_REFERENCE.md** (QUICK LOOKUP)
   - **Purpose:** One-page cheat sheet
   - **Contents:**
     - All endpoints in table format
     - Common curl examples
     - Data models and enums
     - Quick start workflows
     - Important notes
   - **Best for:** Quick lookup while coding
   - **Read time:** 5 minutes

### 4. 📊 **API_ENDPOINTS.json** (STRUCTURED DATA)
   - **Purpose:** Machine-readable endpoint definitions
   - **Contents:**
     - All endpoints in JSON format
     - Organized by resource type
     - Request/response structures
     - Authentication info
   - **Best for:** Integration with tools (Postman, etc.)
     
### 5. 🔄 **DTO_REFERENCE.md** (DATA MODELS)
   - **Purpose:** Reference for all request/response models
   - **Contents:**
     - Request DTO structures
     - Response DTO structures
     - Validation rules
     - Field descriptions
     - Common error formats
   - **Best for:** Understanding data structures
   - **Read time:** 10 minutes

---

## 🎯 Quick Navigation by Use Case

### I want to...

**...understand what the API does**
→ Read: [API_SUMMARY.md](#1--api_summarymd)

**...implement a feature**
→ Read: [API_DOCUMENTATION.md](#2--api_documentationmd) + [DTO_REFERENCE.md](#5--dto_referencemd)

**...quickly look up an endpoint**
→ Read: [QUICK_REFERENCE.md](#3--quick_referencemd)

**...integrate with Postman/API client**
→ Use: [API_ENDPOINTS.json](#4--api_endpointsjson)

**...understand data structures**
→ Read: [DTO_REFERENCE.md](#5--dto_referencemd)

**...set up curl commands for testing**
→ Read: [QUICK_REFERENCE.md](#3--quick_referencemd)

---

## 🔐 Authentication Quick Start

All protected endpoints require a JWT token in the Authorization header:

```
Authorization: Bearer {token}
```

### Get a Token:
1. Register: `POST /api/v1/auth/register`
2. Or Login: `POST /api/v1/auth/login`
3. Use the returned `token` in subsequent requests

### Refresh Token:
When token expires (24 hours), use refresh endpoint:
```
POST /api/v1/auth/refresh
Body: { "refreshToken": "..." }
```

---

## 📋 All Endpoints (Quick Reference)

### 🔑 Authentication (3)
```
POST /api/v1/auth/register
POST /api/v1/auth/login
POST /api/v1/auth/refresh
```

### 🏪 Retailer Profiles (8)
```
POST   /api/v1/retailers
GET    /api/v1/retailers/{id}
GET    /api/v1/retailers/user/{userId}
PUT    /api/v1/retailers/{id}
DELETE /api/v1/retailers/{id}
POST   /api/v1/retailers/nearby-suppliers
GET    /api/v1/retailers/{retailerId}/nearby-suppliers
GET    /api/v1/retailers/me/nearby-suppliers
```

### 🏭 Supplier Profiles (2)
```
POST /api/v1/suppliers
GET  /api/v1/suppliers/nearby
```

### 📦 Orders (5)
```
POST   /api/v1/orders
GET    /api/v1/orders/{id}
GET    /api/v1/orders/retailer/{id}
GET    /api/v1/orders/supplier/{id}
PATCH  /api/v1/orders/{id}/status
```

### ⭐ Ratings (2)
```
POST /api/v1/ratings
GET  /api/v1/ratings/supplier/{id}/stats
```

**Total: 26 Endpoints**

---

## 🧭 Reading Guide by Role

### Frontend Developer (Building Mobile App)

**Day 1 - Setup & Authentication:**
1. Read: [API_SUMMARY.md](#1--api_summarymd)
2. Read: Authentication section in [API_DOCUMENTATION.md](#2--api_documentationmd)
3. Test: Auth endpoints with [QUICK_REFERENCE.md](#3--quick_referencemd) examples

**Day 2 - Data Models:**
1. Read: [DTO_REFERENCE.md](#5--dto_referencemd)
2. Understand: All request/response structures

**Day 3+ - Feature Implementation:**
1. For each feature:
   - Look up endpoint in [QUICK_REFERENCE.md](#3--quick_referencemd)
   - Read detailed docs in [API_DOCUMENTATION.md](#2--api_documentationmd)
   - Reference data models in [DTO_REFERENCE.md](#5--dto_referencemd)

### API Integration Specialist

**Setup:**
1. Import [API_ENDPOINTS.json](#4--api_endpointsjson) into Postman
2. Import API_ENDPOINTS.json into your API client
3. Set up authentication variables

**Testing:**
1. Use curl examples from [QUICK_REFERENCE.md](#3--quick_referencemd)
2. Follow workflows in [API_DOCUMENTATION.md](#2--api_documentationmd)

### QA/Tester

1. Read: [API_SUMMARY.md](#1--api_summarymd) - understand features
2. Use: [QUICK_REFERENCE.md](#3--quick_referencemd) - endpoint reference
3. Use: [API_ENDPOINTS.json](#4--api_endpointsjson) - structured test data
4. Reference: [DTO_REFERENCE.md](#5--dto_referencemd) - validation rules

---

## 💡 Key Features Highlighted

### 1. Location-Based Supplier Discovery
- Find suppliers within a specific radius
- Three search methods (coordinates, profile, authenticated user)
- Results sorted by distance (closest first)
- Uses Haversine formula for accuracy

📖 **Read:** API_DOCUMENTATION.md → "Find Nearby Suppliers" sections

### 2. Order Management
- Create orders with suppliers
- Track status: PENDING → IN_PROGRESS → DELIVERED → CANCELLED
- Full order history

📖 **Read:** API_DOCUMENTATION.md → "Order Management" section

### 3. Supplier Rating System
- Rate suppliers 1-5 with comments
- View rating statistics

📖 **Read:** API_DOCUMENTATION.md → "Ratings System" section

### 4. User Roles & Permissions
- RETAILER: Browse, order, rate
- SUPPLIER: Receive orders, update status
- ADMIN: Reserved for future features

📖 **Read:** API_DOCUMENTATION.md → "Security & Error Handling" section

---

## 🚀 Getting Started (5 Minutes)

### 1. Read Overview (2 min)
```
→ Read: API_SUMMARY.md (sections: API Overview, Total Endpoints)
```

### 2. Understand Authentication (1 min)
```
→ Read: This file (section: Authentication Quick Start)
```

### 3. Test an Endpoint (2 min)
```
→ Copy curl from QUICK_REFERENCE.md → Register User
→ Run it in terminal
→ Save the returned token
```

**That's it! You can now make API calls!**

---

## 📞 Quick Support Guide

### I get a 400 Bad Request error
→ Check: DTO_REFERENCE.md - Validation rules

### I get a 401 Unauthorized error
→ Check: Authentication Quick Start (this file)

### I don't know what fields a request needs
→ Check: DTO_REFERENCE.md - Request DTOs

### I don't know what a response contains
→ Check: DTO_REFERENCE.md - Response DTOs

### I need a curl example
→ Check: QUICK_REFERENCE.md - Common Request/Response Examples

### I want detailed explanation of an endpoint
→ Check: API_DOCUMENTATION.md - find your endpoint

### I want structured data for automation
→ Check: API_ENDPOINTS.json

---

## 🔗 File Relationships

```
Documentation Index
├── API_SUMMARY.md (Overview)
│   ├── Links to API_DOCUMENTATION.md (Details)
│   └── Links to QUICK_REFERENCE.md (Quick lookup)
│
├── API_DOCUMENTATION.md (Comprehensive)
│   ├── References DTO_REFERENCE.md
│   └── Contains examples from QUICK_REFERENCE.md
│
├── QUICK_REFERENCE.md (Quick lookup)
│   ├── References all endpoints
│   ├── References DTO_REFERENCE.md
│   └── Contains curl examples
│
├── DTO_REFERENCE.md (Data Models)
│   ├── Documents all Request DTOs
│   ├── Documents all Response DTOs
│   └── Validation rules
│
└── API_ENDPOINTS.json (Machine Readable)
    └── Structured version of all endpoints
```

---

## ✅ Documentation Checklist

Before sharing with frontend team, verify you have:

- ✅ API_SUMMARY.md - Overview document
- ✅ API_DOCUMENTATION.md - Comprehensive reference
- ✅ QUICK_REFERENCE.md - One-page cheat sheet
- ✅ DTO_REFERENCE.md - Data models reference
- ✅ API_ENDPOINTS.json - Structured endpoint data
- ✅ This INDEX file - Navigation guide

---

## 📊 By the Numbers

| Metric | Count |
|--------|-------|
| Total Endpoints | 26 |
| Authenticated Endpoints | 8 |
| Public Endpoints | 18 |
| Request DTOs | 8 |
| Response DTOs | 7 |
| Enums | 3 |
| Documentation Files | 6 |
| Code Examples | 20+ |

---

## 🎓 Learning Path

### Beginner (First time using the API)
1. API_SUMMARY.md (overview)
2. QUICK_REFERENCE.md (endpoints)
3. Test auth endpoints with curl

### Intermediate (Implementing features)
1. API_DOCUMENTATION.md (pick your endpoint)
2. DTO_REFERENCE.md (understand data)
3. QUICK_REFERENCE.md (examples)

### Advanced (Building complex features)
1. API_DOCUMENTATION.md (workflows section)
2. DTO_REFERENCE.md (validation rules)
3. API_ENDPOINTS.json (integration)

---

## 🔄 Update Schedule

- **Documentation:** Updated whenever API changes
- **Examples:** Tested against running backend
- **Last Updated:** November 16, 2025
- **Backend Version:** 0.0.1-SNAPSHOT

---

## 📝 Notes for Frontend Team

1. **Save your tokens securely** - Don't log them in console
2. **Handle token refresh** - Tokens expire after 24 hours
3. **Validate input** - Check DTO_REFERENCE.md for rules
4. **Handle errors gracefully** - All errors have consistent format
5. **Use nearby-suppliers feature** - Great for UX!

---

## 🎯 Next Steps

1. **Choose your document** - Based on your role (see Navigation Guide above)
2. **Read it** - Takes 5-30 minutes depending on choice
3. **Test an endpoint** - Use QUICK_REFERENCE.md examples
4. **Start implementing** - Reference docs as needed

---

**Ready to integrate? Start with [API_DOCUMENTATION.md](#2--api_documentationmd)!**

---

**Generated:** November 16, 2025  
**API Version:** 1.0.0  
**Backend Version:** smart-s2r-backend 0.0.1-SNAPSHOT  
**Status:** ✅ Ready for Frontend Integration
