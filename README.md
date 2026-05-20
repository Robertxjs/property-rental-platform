# Property Rental Platform
## Business Analyst Presentation

---

## 1. Executive Summary

Property Rental Platform is a web application that connects landlords and tenants in a structured rental workflow.

The current application supports:
- landlord and tenant registration/login;
- apartment listing management;
- tenant browsing and rental request submission;
- landlord approval or rejection of rental requests;
- tenant issue reporting through support tickets;
- landlord visibility and resolution of reported issues.

The business value is a clearer rental process, reduced manual coordination, and a single operational view for property owners and tenants.

---

## 2. Business Problem

The rental process often depends on fragmented communication: phone calls, messages, manual follow-ups, and informal records.

Key pain points:
- tenants do not have a clear view of request status;
- landlords manage listings and requests manually;
- approved tenants need a formal way to report apartment problems;
- issue tracking is hard when problems are handled through informal channels;
- rental decisions and maintenance requests lack centralized traceability.

---

## 3. Target Users

### Landlord

Primary goals:
- publish and manage apartments;
- review rental requests;
- approve or reject tenants;
- monitor issues reported by tenants;
- mark tickets as resolved.

### Tenant

Primary goals:
- create an account and browse available apartments;
- submit rental requests;
- track request status;
- view approved apartments;
- report problems for apartments they are renting.

---

## 4. Value Proposition

The platform creates value by moving the rental journey into a digital workflow.

Business benefits:
- faster communication between tenants and landlords;
- transparent request status;
- better landlord control over apartment availability;
- reduced operational ambiguity;
- structured maintenance reporting;
- reusable foundation for future invoicing, contracts, and payments.

---

## 5. Current Functional Scope

### Authentication

Users can register and log in as either `LANDLORD` or `TENANT`.

### Apartment Management

Landlords can create, update, delete, and view apartments. Apartments include address, building, floor, rooms, size, monthly rent, deposit, and status.

### Rental Requests

Tenants submit rental requests for apartments. Landlords can approve or reject them. Approved requests update apartment status to `OCCUPIED`.

### Ticket Requests

Tenants can report apartment issues only for approved rentals. Landlords can view received tickets and mark them as resolved.

---

## 6. Main Business Workflow

1. A landlord registers and logs in.
2. The landlord creates apartment listings.
3. A tenant registers and logs in.
4. The tenant browses available apartments.
5. The tenant submits a rental request.
6. The landlord reviews the request.
7. The landlord approves or rejects the request.
8. If approved, the apartment becomes occupied.
9. The tenant can report technical problems for the approved apartment.
10. The landlord reviews and resolves ticket requests.

---

## 7. Business Rules

Current rules implemented in the application:
- only users with role `LANDLORD` can create apartments;
- only users with role `TENANT` can submit rental requests;
- rental requests start with status `PENDING`;
- landlord actions change request status to `APPROVED` or `REJECTED`;
- approved rental requests change apartment status to `OCCUPIED`;
- only tenants can create support tickets;
- tenants can create tickets only for apartments with approved rental requests;
- ticket status is limited to `OPEN`, `IN_PROGRESS`, or `RESOLVED`.

---

## 8. Data and System Architecture

The system uses a hybrid persistence model:
- MySQL stores core account and apartment data;
- `requests.json` stores rental requests;
- `tickets.json` stores support tickets.

Backend:
- Spring Boot REST API;
- controller, service, repository layering;
- JPA repositories for database entities.

Frontend:
- static HTML pages;
- CSS styling;
- JavaScript calls to REST endpoints;
- browser `localStorage` for user session data.

---

## 9. Key Entities

### User

Represents both landlords and tenants. The role determines what workflows are available.

### Apartment

Represents the rental asset managed by a landlord.

### RentalRequest

Represents a tenant's intent to rent an apartment and the landlord's decision.

### Ticket

Represents a tenant-reported issue for an approved rental apartment.

---

## 10. API Capabilities

Authentication:
- `POST /api/auth/register`
- `POST /api/auth/login`

Apartments:
- `GET /api/apartments`
- `GET /api/apartments/{id}`
- `POST /api/apartments`
- `PUT /api/apartments/{id}`
- `DELETE /api/apartments/{id}`

Rental requests:
- `POST /api/rental-requests`
- `GET /api/rental-requests/tenant/{tenantId}`
- `GET /api/rental-requests/landlord/{landlordId}`
- `PUT /api/rental-requests/{id}/approve`
- `PUT /api/rental-requests/{id}/reject`

Tickets:
- `POST /api/tickets`
- `GET /api/tickets/tenant/{tenantId}`
- `GET /api/tickets/landlord/{landlordId}`
- `PUT /api/tickets/{id}/status?status=RESOLVED`

---

## 11. Business Analyst Assessment

The application already covers the most important operational rental lifecycle:
- listing creation;
- tenant demand capture;
- landlord decision;
- post-approval issue management.

From a business analysis perspective, the application is a strong MVP because it demonstrates role-based workflows and measurable transaction states.

The next maturity step is to move all operational records into consistent database persistence and introduce stronger security controls.

---

## 12. Gaps and Risks

Current limitations:
- rental requests and tickets are stored in JSON files rather than normalized database tables;
- authentication uses simplified password hashing;
- frontend session management is stored in `localStorage`;
- there is no authorization layer enforcing ownership on every endpoint;
- invoice functionality appears in the initial scope but is not implemented in the current codebase;
- no audit trail exists for approvals, rejections, or ticket status changes.

Business risks:
- data integrity issues under concurrent usage;
- limited scalability due to file-based persistence;
- weaker compliance posture without secure authentication and audit history.

---

## 13. Recommended Roadmap

### Short Term

- Persist rental requests and tickets in MySQL.
- Add backend validation for all DTOs.
- Add clear UI messages for each failed business rule.
- Add test data and automated tests for main workflows.

### Medium Term

- Add Spring Security and role-based authorization.
- Implement invoice management.
- Add landlord/tenant dashboards with summary metrics.
- Add ticket lifecycle support for `IN_PROGRESS`.

### Long Term

- Add contract management.
- Add payment tracking.
- Add notifications for request and ticket status changes.
- Add reporting for occupancy, revenue, and maintenance workload.

---

## 14. Suggested KPIs

Operational KPIs:
- number of active landlords;
- number of active tenants;
- number of listed apartments;
- request approval rate;
- average time from request submission to decision;
- number of open tickets;
- average time to resolve tickets;
- apartment occupancy rate.

These KPIs help evaluate adoption, landlord responsiveness, and operational quality.

---

## 15. Conclusion

The application provides a practical MVP for digital property rental management.

It solves the core business need of connecting landlords and tenants through a structured workflow and adds post-rental support through tickets.

The recommended business direction is to strengthen persistence, security, and reporting so the platform can evolve from a functional prototype into a reliable operational product.
