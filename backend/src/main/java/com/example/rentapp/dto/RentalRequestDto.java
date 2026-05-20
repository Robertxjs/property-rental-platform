package com.example.rentapp.dto;

public class RentalRequestDto {
    private Long apartmentId;
    private Long tenantId;
    private String message;

    // Constructori
    public RentalRequestDto() {}

    // Getters și Setters
    public Long getApartmentId() { return apartmentId; }
    public void setApartmentId(Long apartmentId) { this.apartmentId = apartmentId; }

    public Long getTenantId() { return tenantId; }
    public void setTenantId(Long tenantId) { this.tenantId = tenantId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}