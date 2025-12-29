package com.corporatebank.service;

import com.corporatebank.dto.CreditRequestCreateDTO;
import com.corporatebank.exception.ResourceNotFoundException;
import com.corporatebank.model.CreditRequest;
import com.corporatebank.repo.CreditRequestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CreditRequestService {

    private final CreditRequestRepository repo;

    public CreditRequestService(CreditRequestRepository repo) {
        this.repo = repo;
    }

    // RM → Create Credit Request
    public CreditRequest create(CreditRequestCreateDTO dto, String rmUsername) {

        if (dto.getClientId() == null || dto.getClientId().isBlank()) {
            throw new IllegalArgumentException("Client ID is required");
        }

        CreditRequest req = new CreditRequest();
        req.setClientId(dto.getClientId());
        req.setRequestAmount(dto.getRequestAmount());
        req.setTenureMonths(dto.getTenureMonths());
        req.setPurpose(dto.getPurpose());

        req.setSubmittedBy(rmUsername);
        req.setStatus("PENDING");
        req.setCreatedAt(LocalDateTime.now());

        return repo.save(req);
    }


    // RM → View Own Requests
    public List<CreditRequest> getByRm(String rmUsername) {
        return repo.findBySubmittedBy(rmUsername);
    }


    // Analyst → View All Requests
    public List<CreditRequest> getAll() {
        return repo.findAll();
    }


    // Analyst → Approve / Reject
    public CreditRequest updateStatus(String id, String status, String remarks) {

        CreditRequest req = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Credit request not found with id: " + id)
                );

        if (!status.equalsIgnoreCase("APPROVED")
                && !status.equalsIgnoreCase("REJECTED")) {
            throw new IllegalArgumentException(
                    "Invalid status. Allowed values: APPROVED or REJECTED"
            );
        }

        req.setStatus(status.toUpperCase());
        req.setRemarks(remarks);

        return repo.save(req);
    }
}
