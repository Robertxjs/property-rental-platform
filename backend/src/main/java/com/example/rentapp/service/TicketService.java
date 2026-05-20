package com.example.rentapp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.example.rentapp.dto.TicketRequest;
import com.example.rentapp.model.Apartment;
import com.example.rentapp.model.Ticket;
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
public class TicketService {

    private final ApartmentRepo apartmentRepository;
    private final UserRepo userRepository;
    private final ObjectMapper objectMapper;
    private final String FILE_PATH = "tickets.json";

    public TicketService(ApartmentRepo apartmentRepository, UserRepo userRepository) {
        this.apartmentRepository = apartmentRepository;
        this.userRepository = userRepository;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public Ticket createTicket(TicketRequest dto) {
        Apartment apartment = apartmentRepository.findById(dto.getApartmentId())
                .orElseThrow(() -> new RuntimeException("Apartamentul nu a fost găsit!"));
        User tenant = userRepository.findById(dto.getTenantId())
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu există!"));

        Ticket ticket = new Ticket();
        ticket.setId(System.currentTimeMillis());
        ticket.setApartment(apartment);
        ticket.setTenant(tenant);
        ticket.setTitle(dto.getTitle());
        ticket.setDescription(dto.getDescription());

        try {
            List<Ticket> allTickets = readTicketsFromFile();
            allTickets.add(ticket);
            writeTicketsToFile(allTickets);
        } catch (IOException e) {
            throw new RuntimeException("Eroare la salvarea tichetului: " + e.getMessage());
        }
        return ticket;
    }

    public List<Ticket> getTicketsByTenant(Long tenantId) {
        try {
            return readTicketsFromFile().stream()
                    .filter(t -> t.getTenant() != null && t.getTenant().getId().equals(tenantId))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public List<Ticket> getTicketsByLandlord(Long landlordId) {
        try {
            return readTicketsFromFile().stream()
                    .filter(t -> t.getApartment() != null && 
                                 t.getApartment().getLandlord() != null && 
                                 t.getApartment().getLandlord().getId().equals(landlordId))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public Ticket updateTicketStatus(Long ticketId, String status) {
        try {
            List<Ticket> allTickets = readTicketsFromFile();
            for (Ticket t : allTickets) {
                if (t.getId().equals(ticketId)) {
                    t.setStatus(status.toUpperCase());
                    writeTicketsToFile(allTickets);
                    return t;
                }
            }
            throw new RuntimeException("Tichetul nu a fost găsit!");
        } catch (IOException e) {
            throw new RuntimeException("Eroare la actualizarea tichetului.");
        }
    }

    private List<Ticket> readTicketsFromFile() throws IOException {
        File file = new File(FILE_PATH);
        if (!file.exists() || file.length() == 0) return new ArrayList<>();
        return objectMapper.readValue(file, new TypeReference<List<Ticket>>() {});
    }

    private void writeTicketsToFile(List<Ticket> tickets) throws IOException {
        File file = new File(FILE_PATH);
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, tickets);
    }
}