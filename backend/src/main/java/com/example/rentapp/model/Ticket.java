package com.example.rentapp.model;

import java.time.LocalDateTime;

public class Ticket {
    private Long id;
    private Apartment apartment;
    private User tenant;
    private String title;
    private String description;
    private String status = "OPEN"; // OPEN, IN_PROGRESS, RESOLVED
    private LocalDateTime createdAt = LocalDateTime.now();

    public Ticket() {}

    // Getters și Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Apartment getApartment() { return apartment; }
    public void setApartment(Apartment apartment) { this.apartment = apartment; }
    public User getTenant() { return tenant; }
    public void setTenant(User tenant) { this.tenant = tenant; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}