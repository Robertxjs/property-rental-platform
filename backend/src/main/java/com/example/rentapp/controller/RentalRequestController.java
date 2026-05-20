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
}