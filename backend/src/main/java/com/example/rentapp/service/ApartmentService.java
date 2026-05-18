package com.example.rentapp.service;

import com.example.rentapp.dto.ApartmentRequest;
import com.example.rentapp.model.Apartment;
import com.example.rentapp.model.User;
import com.example.rentapp.repo.ApartmentRepo;
import com.example.rentapp.repo.UserRepo;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ApartmentService {

    private final ApartmentRepo apartmentRepository;
    private final UserRepo userRepository;

    public ApartmentService(ApartmentRepo apartmentRepository, UserRepo userRepository) {
        this.apartmentRepository = apartmentRepository;
        this.userRepository = userRepository;
    }

    public List<Apartment> getAllApartments() {
        return apartmentRepository.findAll();
    }

    public Apartment getApartmentById(Long id) {
        return apartmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Apartamentul nu a fost găsit!"));
    }

    public Apartment createApartment(ApartmentRequest request) {
        User landlord = userRepository.findById(request.getLandlordId())
                .orElseThrow(() -> new RuntimeException("Proprietarul nu există!"));

        if (!"LANDLORD".equals(landlord.getRole())) {
            throw new RuntimeException("Doar un LANDLORD poate adăuga apartamente!");
        }

        Apartment apartment = new Apartment();
        apartment.setLandlord(landlord);
        apartment.setAddress(request.getAddress());
        apartment.setBuilding(request.getBuilding());
        apartment.setFloor(request.getFloor());
        apartment.setRooms(request.getRooms());
        apartment.setSize(request.getSize());
        apartment.setMonthlyRent(request.getMonthlyRent());
        apartment.setDeposit(request.getDeposit());
        if (request.getStatus() != null) apartment.setStatus(request.getStatus().toUpperCase());

        return apartmentRepository.save(apartment);
    }

    public Apartment updateApartment(Long id, ApartmentRequest request) {
        Apartment apartment = getApartmentById(id);
        
        apartment.setAddress(request.getAddress());
        apartment.setBuilding(request.getBuilding());
        apartment.setFloor(request.getFloor());
        apartment.setRooms(request.getRooms());
        apartment.setSize(request.getSize());
        apartment.setMonthlyRent(request.getMonthlyRent());
        apartment.setDeposit(request.getDeposit());
        if (request.getStatus() != null) apartment.setStatus(request.getStatus().toUpperCase());

        return apartmentRepository.save(apartment);
    }

    public void deleteApartment(Long id) {
        Apartment apartment = getApartmentById(id);
        apartmentRepository.delete(apartment);
    }
}