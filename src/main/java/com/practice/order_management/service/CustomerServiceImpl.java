package com.practice.order_management.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.practice.order_management.models.Customer;
import com.practice.order_management.repository.CustomerRepoImpl;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepoImpl repo;

    public CustomerServiceImpl(CustomerRepoImpl repo) {
        this.repo = repo;
    }

    @Override
    public ResponseEntity<List<Customer>> findAllCustomers() {
        List<Customer> customers = repo.findAllCustomers();
        if (customers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(customers);
        }
    }

    @Override
    public ResponseEntity<Customer> findCustomerById(int id) {
        Customer customer = repo.findCustomerById(id);
        if (customer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }

    @Override
    public ResponseEntity<List<Customer>> findCustomersByNameContains(String pattern) {
        List<Customer> customers = repo.findCustomersByNameContains(pattern);
        if (customers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(customers);
    }

    @Override
    public ResponseEntity<Customer> findCustomerByEmail(String email) {
        Customer customer = repo.findCustomerByEmail(email);
        if (customer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }

    @Override
    public ResponseEntity<Customer> saveCustomer(Customer customer) {
        // 1. Setting the current TIMESTAMP to all the orders
        // 2. Associating each order with their customer
        customer.setId(0); // Enforcing id as 0 so that JPA treats it as a new object
        if (!customer.getOrders().isEmpty()) {
            customer.getOrders().stream().forEach(order -> order.setOrderedAt(LocalDateTime.now()));
            customer.getOrders().stream().forEach(order -> order.setCustomer(customer));

        }
        Customer savedCustomer = repo.saveCustomer(customer);
        if (savedCustomer == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
        }
    }

    @Override
    public ResponseEntity<Customer> updateCustomer(Customer customer) {
        if (!customer.getOrders().isEmpty()) {
            customer.getOrders().stream().filter(order -> order.getOrderedAt() == null)
                    .forEach(order -> order.setOrderedAt(LocalDateTime.now()));
            customer.getOrders().stream().forEach(order -> order.setCustomer(customer));
        }
        Customer savedCustomer = repo.saveCustomer(customer);
        if (savedCustomer == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(savedCustomer);
        }
    }

    @Override
    public ResponseEntity<Customer> deleteCustomerById(int id) {
        Customer deletedCustomer = repo.deleteCustomer(id);
        if (deletedCustomer == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(deletedCustomer);
    }

}
