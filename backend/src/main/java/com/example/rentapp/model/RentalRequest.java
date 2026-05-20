package com.example.rentapp.model;

import java.time.LocalDateTime;

public class RentalRequest {
    private Long id;
    private Apartment apartment;
    private User tenant;
    private RequestStatus status;
    private String message;
    private LocalDateTime createdAt = LocalDateTime.now();

    // Constructor public gol (obligatoriu pentru ca Jackson să poată citi corect din JSON)
    public RentalRequest() {}

    // Getters și Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Apartment getApartment() { return apartment; }
    public void setApartment(Apartment apartment) { this.apartment = apartment; }

    public User getTenant() { return tenant; }
    public void setTenant(User tenant) { this.tenant = tenant; }

    public RequestStatus getStatus() { return status; }
    public void setStatus(RequestStatus status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}