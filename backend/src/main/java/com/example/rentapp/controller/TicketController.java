package com.example.rentapp.controller;

import com.example.rentapp.dto.TicketRequest;
import com.example.rentapp.model.Ticket;
import com.example.rentapp.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "*")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TicketRequest dto) {
        try {
            return ResponseEntity.ok(ticketService.createTicket(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<Ticket>> getByTenant(@PathVariable Long tenantId) {
        return ResponseEntity.ok(ticketService.getTicketsByTenant(tenantId));
    }

    @GetMapping("/landlord/{landlordId}")
    public ResponseEntity<List<Ticket>> getByLandlord(@PathVariable Long landlordId) {
        return ResponseEntity.ok(ticketService.getTicketsByLandlord(landlordId));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            return ResponseEntity.ok(ticketService.updateTicketStatus(id, status));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}