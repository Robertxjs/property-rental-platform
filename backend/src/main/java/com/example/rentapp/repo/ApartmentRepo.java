package com.example.rentapp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.rentapp.model.Apartment;

public interface ApartmentRepo extends JpaRepository<Apartment, Long>{

    List<Apartment> findByLandlordId(Long landlordId);
    
}
