package com.example.rentapp.dto;

public class TicketRequest {
    private Long apartmentId;
    private Long tenantId;
    private String title;
    private String description;

    public TicketRequest() {}

    // Getters și Setters
    public Long getApartmentId() { return apartmentId; }
    public void setApartmentId(Long apartmentId) { this.apartmentId = apartmentId; }
    public Long getTenantId() { return tenantId; }
    public void setTenantId(Long tenantId) { this.tenantId = tenantId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}