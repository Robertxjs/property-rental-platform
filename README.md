# Property Rental Platform — Phase 1

## Goal
Phase 1 implements the core rental workflow between two user roles:

- Landlord
- Tenant

The platform allows landlords to publish rental properties and tenants to view available properties, submit rental requests, and manage basic invoices.

## Phase 1 Features

### Landlord
- Register / login
- Create property listings
- View own properties
- Update property details
- Delete property listings
- View tenant rental requests
- Approve or reject requests
- Create invoices for tenants

### Tenant
- Register / login
- Browse available properties
- View property details
- Submit rental request
- View request status
- View invoices

## Core Entities
- User
- Landlord
- Tenant
- Property
- RentalRequest
- Invoice

## Suggested Tech Stack
- Frontend: HTML, CSS, JavaScript
- Backend: Spring Boot
- Database: MySQL

## Phase 1 API Scope

### Authentication
- POST `/api/auth/register`
- POST `/api/auth/login`

### Properties
- GET `/api/properties`
- GET `/api/properties/{id}`
- POST `/api/properties`
- PUT `/api/properties/{id}`
- DELETE `/api/properties/{id}`

### Rental Requests
- POST `/api/rental-requests`
- GET `/api/rental-requests/tenant/{tenantId}`
- GET `/api/rental-requests/landlord/{landlordId}`
- PUT `/api/rental-requests/{id}/approve`
- PUT `/api/rental-requests/{id}/reject`

### Invoices
- POST `/api/invoices`
- GET `/api/invoices/tenant/{tenantId}`
- GET `/api/invoices/landlord/{landlordId}`

## UML Class Diagram

```plantuml
@startuml
title Property Rental Platform - Phase 1

class User {
  - id: Long
  - fullName: String
  - email: String
  - password: String
  - role: Role
}

enum Role {
  LANDLORD
  TENANT
}

class Property {
  - id: Long
  - title: String
  - description: String
  - address: String
  - pricePerMonth: BigDecimal
  - available: boolean
}

class RentalRequest {
  - id: Long
  - status: RequestStatus
  - message: String
  - createdAt: LocalDateTime
}

enum RequestStatus {
  PENDING
  APPROVED
  REJECTED
}

class Invoice {
  - id: Long
  - amount: BigDecimal
  - dueDate: LocalDate
  - paid: boolean
}

User "1" --> "many" Property : landlord owns
User "1" --> "many" RentalRequest : tenant sends
Property "1" --> "many" RentalRequest : receives
RentalRequest "1" --> "0..1" Invoice : creates
User "1" --> "many" Invoice : tenant receives

@enduml
