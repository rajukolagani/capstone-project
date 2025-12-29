package com.corporatebank.controller;

import com.corporatebank.dto.CreditRequestCreateDTO;
import com.corporatebank.dto.CreditRequestUpdateDTO;
import com.corporatebank.model.CreditRequest;
import com.corporatebank.service.CreditRequestService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/credit-requests")
public class CreditRequestController {

    private final CreditRequestService service;

    public CreditRequestController(CreditRequestService service) {
        this.service = service;
    }

    @PostMapping
    public CreditRequest create(@Valid @RequestBody CreditRequestCreateDTO dto, Authentication auth) {
        return service.create(dto, auth.getName());
    }

    @GetMapping
    public List<CreditRequest> get(Authentication auth) {

        boolean isAnalyst = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ANALYST") || a.getAuthority().equals("ANALYST"));

        return isAnalyst ? service.getAll() : service.getByRm(auth.getName());
    }

    @PutMapping("/{id}")
    public CreditRequest updateStatus(@PathVariable String id, @Valid @RequestBody CreditRequestUpdateDTO dto) {
        return service.updateStatus(id, dto.getStatus(), dto.getRemarks());
    }
}