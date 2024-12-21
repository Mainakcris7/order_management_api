package com.practice.order_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.order_management.models.Client;

public interface ClientRepo extends JpaRepository<Client, Integer> {
    Client findByName(String name);
}
