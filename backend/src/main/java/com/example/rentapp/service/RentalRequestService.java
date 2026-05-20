package com.example.rentapp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.example.rentapp.dto.RentalRequestDto;
import com.example.rentapp.model.Apartment;
import com.example.rentapp.model.RentalRequest;
import com.example.rentapp.model.RequestStatus;
import com.example.rentapp.model.User;
import com.example.rentapp.repo.ApartmentRepo;
import com.example.rentapp.repo.UserRepo;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalRequestService {

    private final ApartmentRepo apartmentRepository;
    private final UserRepo userRepository;
    private final ObjectMapper objectMapper;
    private final String FILE_PATH = "requests.json"; // Numele fișierului local JSON

    public RentalRequestService(ApartmentRepo apartmentRepository, UserRepo userRepository) {
        this.apartmentRepository = apartmentRepository;
        this.userRepository = userRepository;
        
        // Configuram Jackson pentru a lucra corect cu formatul JSON si LocalDateTime
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    // 1. Metoda pentru SALVARE în fișierul JSON
    public RentalRequest createRequest(RentalRequestDto dto) {
        // Validăm dacă apartamentul există în baza de date MySQL existentă
        Apartment apartment = apartmentRepository.findById(dto.getApartmentId())
                .orElseThrow(() -> new RuntimeException("Apartamentul specificat nu a fost găsit!"));

        // Validăm dacă utilizatorul există în baza de date
        User tenant = userRepository.findById(dto.getTenantId())
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu există!"));

        // Regula de business: Doar un utilizator cu rolul TENANT poate trimite cereri
        if (!"TENANT".equals(tenant.getRole())) {
            throw new RuntimeException("Doar un utilizator cu rolul TENANT poate face o cerere de închiriere!");
        }

        // Construim obiectul cererii
        RentalRequest request = new RentalRequest();
        request.setId(System.currentTimeMillis()); // Generăm un ID unic simulat pe baza timpului curent
        request.setApartment(apartment);
        request.setTenant(tenant);
        request.setMessage(dto.getMessage());
        request.setStatus(RequestStatus.PENDING); // Implicit statusul este PENDING

        try {
            // Citim lista actuală, adăugăm cererea nouă și rescriem fișierul
            List<RentalRequest> allRequests = readRequestsFromFile();
            allRequests.add(request);
            writeRequestsToFile(allRequests);
        } catch (IOException e) {
            throw new RuntimeException("Eroare la salvarea în fișierul JSON local: " + e.getMessage());
        }

        return request;
    }

    // 2. Metoda pentru CITIRE și FILTRARE din fișierul JSON (pentru viitoarea pagină my-requests)
    public List<RentalRequest> getRequestsByTenant(Long tenantId) {
        try {
            List<RentalRequest> allRequests = readRequestsFromFile();
            // Filtrăm lista din JSON și returnăm doar cererile ce aparțin acestui tenantId
            return allRequests.stream()
                    .filter(req -> req.getTenant() != null && req.getTenant().getId().equals(tenantId))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    // --- Funcții ajutătoare de citire/scriere pe disc ---
    private List<RentalRequest> readRequestsFromFile() throws IOException {
        File file = new File(FILE_PATH);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }
        return objectMapper.readValue(file, new TypeReference<List<RentalRequest>>() {});
    }

    private void writeRequestsToFile(List<RentalRequest> requests) throws IOException {
        File file = new File(FILE_PATH);
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, requests);
    }
}