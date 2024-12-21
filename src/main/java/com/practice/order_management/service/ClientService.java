package com.practice.order_management.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.practice.order_management.models.Client;
import com.practice.order_management.repository.ClientRepo;
import com.practice.order_management.security.JwtUtils;

@Service
public class ClientService {

    private ClientRepo repo;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtils jwtUtils;

    ClientService(ClientRepo repo, PasswordEncoder encoder, AuthenticationManager authManager) {
        this.repo = repo;
        this.passwordEncoder = encoder;
        this.authManager = authManager;
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

    public String checkLogin(Client client) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(client.getName(), client.getPassword()));
        if (auth.isAuthenticated()) {
            return jwtUtils.generateJWTToken(client.getName());
        }
        return null;
    }
}
