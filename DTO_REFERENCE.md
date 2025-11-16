# Data Transfer Objects (DTOs) Reference

## Request DTOs

### RegisterRequest
```json
{
  "fullName": "string (required)",
  "phone": "string (required, unique)",
  "email": "string",
  "password": "string (required)",
  "role": "RETAILER | SUPPLIER | ADMIN (required)"
}
```

### LoginRequest
```json
{
  "phone": "string (required)",
  "password": "string (required)"
}
```

### RefreshRequest
```json
{
  "refreshToken": "string (required)"
}
```

### RetailerProfileRequest
```json
{
  "shopName": "string (required)",
  "address": "string",
  "latitude": "number (decimal degrees)",
  "longitude": "number (decimal degrees)",
  "description": "string"
}
```

### SupplierProfileRequest
```json
{
  "userId": "number (required)",
  "businessName": "string (required)",
  "address": "string",
  "latitude": "number (decimal degrees)",
  "longitude": "number (decimal degrees)",
  "description": "string"
}
```

### CreateOrderRequest
```json
{
  "supplierId": "number (required)",
  "totalAmount": "number (decimal)",
  "notes": "string"
}
```

### UpdateOrderStatusRequest
```json
{
  "status": "PENDING | IN_PROGRESS | DELIVERED | CANCELLED (required)"
}
```

### RatingRequest
```json
{
  "supplierId": "number (required)",
  "score": "number 1-5 (required)",
  "comment": "string"
}
```

### NearbySuppliersRequest
```json
{
  "latitude": "number (required, decimal degrees)",
  "longitude": "number (required, decimal degrees)",
  "radiusKm": "number (optional, default: 5.0)"
}
```

---

## Response DTOs

### LoginResponse
```json
{
  "userId": "number",
  "fullName": "string",
  "phone": "string",
  "role": "string (RETAILER | SUPPLIER | ADMIN)",
  "token": "string (JWT access token)",
  "refreshToken": "string (JWT refresh token)"
}
```

### RetailerProfileResponse
```json
{
  "id": "number",
  "userId": "number",
  "shopName": "string",
  "address": "string",
  "latitude": "number",
  "longitude": "number",
  "description": "string"
}
```

### SupplierProfileResponse
```json
{
  "id": "number",
  "userId": "number",
  "businessName": "string",
  "address": "string",
  "latitude": "number",
  "longitude": "number",
  "description": "string"
}
```

### SupplierSuggestionResponse
```json
{
  "supplierId": "number",
  "userId": "number",
  "businessName": "string",
  "address": "string",
  "phone": "string",
  "email": "string",
  "latitude": "number",
  "longitude": "number",
  "description": "string",
  "distanceKm": "number (calculated using Haversine formula)"
}
```

### OrderResponse
```json
{
  "id": "number",
  "retailerId": "number",
  "supplierId": "number",
  "status": "PENDING | IN_PROGRESS | DELIVERED | CANCELLED",
  "totalAmount": "number (BigDecimal)",
  "notes": "string",
  "createdAt": "ISO 8601 timestamp",
  "updatedAt": "ISO 8601 timestamp",
  "deliveredAt": "ISO 8601 timestamp"
}
```

### RatingResponse
```json
{
  "id": "number",
  "retailerId": "number",
  "supplierId": "number",
  "score": "number (1-5)",
  "comment": "string",
  "createdAt": "ISO 8601 timestamp"
}
```

### RatingStatsResponse
```json
{
  "average": "number (double)",
  "count": "number (total ratings)"
}
```

### UserResponse
```json
{
  "id": "number",
  "fullName": "string",
  "phone": "string",
  "email": "string",
  "role": "RETAILER | SUPPLIER | ADMIN",
  "createdAt": "ISO 8601 timestamp"
}
```

---

## Error Response Format

### Standard Error Response
```json
{
  "error": "string (error type)",
  "message": "string (detailed error message)"
}
```

### Validation Error Response (400 Bad Request)
```json
{
  "error": "Bad Request",
  "message": "Validation failed",
  "details": {
    "fieldName": "error message"
  }
}
```

---

## Enums & Constants

### Role Enum
```
RETAILER - Retail business
SUPPLIER - Wholesale/supplier business
ADMIN    - Administrator
```

### OrderStatus Enum
```
PENDING      - Order placed, waiting for supplier
IN_PROGRESS  - Supplier is preparing/shipping
DELIVERED    - Order delivered to retailer
CANCELLED    - Order was cancelled
```

### Rating Score Values
```
1 - Very Poor
2 - Poor
3 - Average
4 - Good
5 - Excellent
```

---

## Field Validation Rules

### User Fields
- **fullName:** Required, not blank, string
- **phone:** Required, not blank, unique, format: +254XXXXXXXXX
- **email:** Optional, valid email format
- **password:** Required, not blank, minimum 6 characters recommended
- **role:** Required, one of: RETAILER, SUPPLIER, ADMIN

### Profile Fields
- **shopName/businessName:** Required, not blank
- **address:** Optional, string
- **latitude:** Optional, decimal number (-90 to 90)
- **longitude:** Optional, decimal number (-180 to 180)
- **description:** Optional, string

### Order Fields
- **supplierId:** Required, must exist in database
- **totalAmount:** Optional, decimal/BigDecimal
- **notes:** Optional, string
- **status:** Required for updates, valid order status

### Rating Fields
- **supplierId:** Required, must exist
- **score:** Required, integer between 1 and 5 inclusive
- **comment:** Optional, string

### Location Fields
- **latitude:** Decimal degrees, range: -90 to 90
- **longitude:** Decimal degrees, range: -180 to 180
- **radiusKm:** Positive decimal number (e.g., 5.0, 10.5)

---

## Data Type Mappings

| Java Type | JSON Type | Example |
|-----------|-----------|---------|
| Long | number | 123 |
| String | string | "hello" |
| BigDecimal | number | 25000.50 |
| Double | number | 4.5 |
| Integer | number | 5 |
| Boolean | boolean | true |
| OffsetDateTime | string (ISO 8601) | "2025-11-16T10:30:00+03:00" |
| Enum | string | "RETAILER" |

---

## Common Response Patterns

### Array Response (Multiple Items)
```json
[
  { ...item1... },
  { ...item2... },
  { ...item3... }
]
```

### Single Item Response
```json
{
  ...item details...
}
```

### Empty List Response
```json
[]
```

### No Content Response (204)
```
(empty body)
```

---

## Timestamp Format

All timestamps use ISO 8601 format with timezone:
```
2025-11-16T10:30:00+03:00
```

Components:
- Date: YYYY-MM-DD
- Time: HH:MM:SS
- Timezone: +HH:MM (Nairobi is UTC+3, represented as +03:00)

---

## Query Parameter Reference

| Parameter | Type | Default | Range | Example |
|-----------|------|---------|-------|---------|
| radiusKm | number | 5.0 | 0.1 - 100 | ?radiusKm=10 |
| lat | number | - | -90 to 90 | ?lat=-1.2833 |
| lon | number | - | -180 to 180 | ?lon=36.8167 |
| id | number | - | > 0 | /retailers/123 |
| userId | number | - | > 0 | /retailers/user/123 |

---

## Request Header Requirements

### Standard Headers (all requests)
```
Content-Type: application/json
```

### Authentication Headers (protected endpoints)
```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIrMjU0NzEyMzQ1Njc4Iiwicm9sZSI6IlJFVEFJTEVSIiwiZXhwIjoxNjMyNDU5MjAwfQ.signature
```

### Optional Headers
```
Accept: application/json
Accept-Language: en-US
```

---

**DTO Reference Generated:** November 16, 2025
