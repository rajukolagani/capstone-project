package com.corporatebank.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import com.corporatebank.model.Client;
import com.corporatebank.repo.ClientRepository;
import com.corporatebank.exception.ResourceNotFoundException;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
    @Mock private ClientRepository repo;
    @InjectMocks private ClientService clientService;

    @Test
    void testGetMyClients() {
        when(repo.findByRmUsername("raju")).thenReturn(Collections.singletonList(new Client()));
        List<Client> result = clientService.getMyClients("raju");
        assertEquals(1, result.size());
    }

    @Test
    void testUpdateClient_Success() {
        Client existing = new Client();
        existing.setId("1");
        existing.setRmUsername("raju");

        when(repo.findById("1")).thenReturn(Optional.of(existing));
        when(repo.save(any())).thenReturn(existing);

        Client result = clientService.updateClient("1", new Client());
        assertNotNull(result);
        assertEquals("raju", result.getRmUsername());
    }
}