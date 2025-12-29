package com.corporatebank.controller;

import com.corporatebank.model.Client;
import com.corporatebank.service.ClientService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }
    @PostMapping("/onboard")
    public Client createClient(@RequestBody Client client, Authentication auth) {
        String rmId = auth.getName();
        return service.createClient(client, rmId);
    }

    @GetMapping("/all")
    public List<Client> getMyClients(Authentication auth) {
        return service.getMyClients(auth.getName());
    }

    @PutMapping("/{id}")
    public Client updateClient(@PathVariable String id, @RequestBody Client client) {
        return service.updateClient(id, client);
    }
}