package com.practice.order_management.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.practice.order_management.models.Client;
import com.practice.order_management.repository.ClientRepo;

@Service
public class ClientService {

    private ClientRepo repo;
    private PasswordEncoder passwordEncoder;

    ClientService(ClientRepo repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.passwordEncoder = encoder;
    }

    public ResponseEntity<Client> registerClient(Client client) {
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        Client registeredCLient = repo.save(client);
        if (registeredCLient != null) {
            return ResponseEntity.ok(registeredCLient);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = repo.findAll();
        if (clients.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(clients);
        }
    }
}
