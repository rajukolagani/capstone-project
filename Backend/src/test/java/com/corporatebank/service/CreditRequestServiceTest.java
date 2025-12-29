package com.corporatebank.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import com.corporatebank.model.CreditRequest;
import com.corporatebank.repo.CreditRequestRepository;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CreditRequestServiceTest {
    @Mock private CreditRequestRepository repo;
    @InjectMocks private CreditRequestService service;

    @Test
    void testUpdateStatus_InvalidStatus() {
        CreditRequest req = new CreditRequest();
        when(repo.findById("1")).thenReturn(Optional.of(req));
        assertThrows(IllegalArgumentException.class, () -> service.updateStatus("1", "PENDING", "remarks"));
    }

    @Test
    void testUpdateStatus_Success() {
        CreditRequest req = new CreditRequest();
        when(repo.findById("1")).thenReturn(Optional.of(req));
        when(repo.save(any())).thenReturn(req);
        CreditRequest result = service.updateStatus("1", "APPROVED", "Good");
        assertEquals("APPROVED", result.getStatus());
    }
}