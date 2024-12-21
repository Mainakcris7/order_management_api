package com.practice.order_management.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.order_management.models.Client;
import com.practice.order_management.service.ClientService;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService service;

    @GetMapping("")
    public ResponseEntity<List<Client>> getAllClients() {
        return service.getAllClients();
    }

    @PostMapping("")
    public ResponseEntity<Client> registerClient(@Valid @RequestBody Client client) {
        return service.registerClient(client);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginClient(@Valid @RequestBody Client client) {
        String jwtToken = service.checkLogin(client);
        if (jwtToken != null) {
            Map<String, String> response = new HashMap<>();
            response.put("client_name", client.getName());
            response.put("jwt", jwtToken);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

}
