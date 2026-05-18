package com.example.rentapp.dto;

public class ApartmentRequest {
    private Long landlordId;
    private String address;
    private String building;
    private String floor;
    private int rooms;
    private double size;
    private double monthlyRent;
    private double deposit;
    private String status;

    // Getters și Setters
    public Long getLandlordId() { return landlordId; }
    public void setLandlordId(Long landlordId) { this.landlordId = landlordId; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getBuilding() { return building; }
    public void setBuilding(String building) { this.building = building; }
    public String getFloor() { return floor; }
    public void setFloor(String floor) { this.floor = floor; }
    public int getRooms() { return rooms; }
    public void setRooms(int rooms) { this.rooms = rooms; }
    public double getSize() { return size; }
    public void setSize(double size) { this.size = size; }
    public double getMonthlyRent() { return monthlyRent; }
    public void setMonthlyRent(double monthlyRent) { this.monthlyRent = monthlyRent; }
    public double getDeposit() { return deposit; }
    public void setDeposit(double deposit) { this.deposit = deposit; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}