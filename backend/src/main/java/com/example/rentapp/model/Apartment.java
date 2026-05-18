package com.example.rentapp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "apartments")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "landlord_id", nullable = false)
    private User landlord; // Legătura FK către tabela users

    private String address;
    private String building;
    private String floor;
    private int rooms;
    private double size;

    @Column(name = "monthly_rent")
    private double monthlyRent;

    private double deposit;
    private String status = "AVAILABLE"; // AVAILABLE, OCCUPIED, MAINTENANCE

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Constructori
    public Apartment() {}

    // Getters și Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getLandlord() { return landlord; }
    public void setLandlord(User landlord) { this.landlord = landlord; }
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
    public LocalDateTime getCreatedAt() { return createdAt; }
}