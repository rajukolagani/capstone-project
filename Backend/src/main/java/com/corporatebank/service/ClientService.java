package com.corporatebank.service;

import com.corporatebank.exception.ResourceNotFoundException;
import com.corporatebank.model.Client;
import com.corporatebank.repo.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository repo;

    public ClientService(ClientRepository repo) {
        this.repo = repo;
    }

    public Client createClient(Client client, String rmUsername) {
        client.setRmUsername(rmUsername);
        return repo.save(client);
    }

    public List<Client> getMyClients(String rmUsername) {
        return repo.findByRmUsername(rmUsername);
    }

    public Client updateClient(String id, Client updated) {
        Client existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));

        // Preserve internal fields that shouldn't be changed by the update form
        updated.setId(existing.getId());
        updated.setRmUsername(existing.getRmUsername());

        return repo.save(updated);
    }
}