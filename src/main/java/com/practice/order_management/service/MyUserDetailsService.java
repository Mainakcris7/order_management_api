package com.practice.order_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.practice.order_management.models.Client;
import com.practice.order_management.repository.ClientRepo;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private ClientRepo clientRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepo.findByName(username);
        if (client == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new ClientDetails(client);
    }

}
