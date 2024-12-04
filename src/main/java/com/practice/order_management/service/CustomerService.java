package com.practice.order_management.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.practice.order_management.models.Customer;

public interface CustomerService {
    // GET methods
    public ResponseEntity<List<Customer>> findAllCustomers();

    public ResponseEntity<Customer> findCustomerById(int id);

    public ResponseEntity<Customer> findCustomerByEmail(String email);

    public ResponseEntity<List<Customer>> findCustomersByNameContains(String pattern);

    // POST methods
    public ResponseEntity<Customer> saveCustomer(Customer customer);

    // PUT methods
    public ResponseEntity<Customer> updateCustomer(Customer customer);

    // DELETE methods
    public ResponseEntity<Customer> deleteCustomerById(int id);
}
