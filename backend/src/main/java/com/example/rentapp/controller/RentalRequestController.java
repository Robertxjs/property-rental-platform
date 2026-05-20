package com.example.rentapp.controller;

import com.example.rentapp.dto.RentalRequestDto;
import com.example.rentapp.model.RentalRequest;
import com.example.rentapp.service.RentalRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rental-requests")
@CrossOrigin(origins = "*") //
public class RentalRequestController {

    private final RentalRequestService rentalRequestService;

    public RentalRequestController(RentalRequestService rentalRequestService) {
        this.rentalRequestService = rentalRequestService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody RentalRequestDto requestDto) {
        try {
            RentalRequest savedRequest = rentalRequestService.createRequest(requestDto);
            return ResponseEntity.ok(savedRequest);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<RentalRequest>> getByTenant(@PathVariable Long tenantId) {
        List<RentalRequest> tenantRequests = rentalRequestService.getRequestsByTenant(tenantId);
        return ResponseEntity.ok(tenantRequests);
    }
    @GetMapping("/landlord/{landlordId}")
public ResponseEntity<List<RentalRequest>> getByLandlord(@PathVariable Long landlordId) {
    List<RentalRequest> landlordRequests = rentalRequestService.getRequestsByLandlord(landlordId);
    return ResponseEntity.ok(landlordRequests);
}
    // Endpoint pentru aprobarea cererii (PUT /api/rental-requests/{id}/approve)
    @PutMapping("/{id}/approve")
    public ResponseEntity<?> approveRequest(@PathVariable Long id) {
        try {
            RentalRequest updatedRequest = rentalRequestService.processRequest(id, "approve");
            return ResponseEntity.ok(updatedRequest);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint pentru respingerea cererii (PUT /api/rental-requests/{id}/reject)
    @PutMapping("/{id}/reject")
    public ResponseEntity<?> rejectRequest(@PathVariable Long id) {
        try {
            RentalRequest updatedRequest = rentalRequestService.processRequest(id, "reject");
            return ResponseEntity.ok(updatedRequest);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }   
}