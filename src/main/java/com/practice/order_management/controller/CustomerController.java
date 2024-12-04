package com.practice.order_management.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.practice.order_management.models.Customer;
import com.practice.order_management.service.CustomerServiceImpl;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerServiceImpl service;

    @GetMapping("")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return service.findAllCustomers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable int id) {
        return service.findCustomerById(id);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Customer>> getCustomersByNameContains(@PathVariable String name) {
        return service.findCustomersByNameContains(name);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Customer> getCustomerByEmail(@PathVariable String email) {
        return service.findCustomerByEmail(email);
    }

    // Format -->
    // {
    // "id": 2,
    // "name": "sinchana",
    // "email": "csinchana19@gmail.com",
    // "orders": [
    // {
    // "name": "Sweater",
    // "quantity": 1,
    // "price": 299
    // }
    // ]
    // }
    // The customer object we want to update, MUST SPECIFY THE ID of that object.
    // Also, If we want to add any order, just add the orders, without adding the
    // previous orders, it will automagically append it, without changing the
    // previous orders
    @PutMapping("")
    public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody Customer customer) {
        return service.updateCustomer(customer);
    }

    @PostMapping("")
    public ResponseEntity<Customer> saveCustomer(@Valid @RequestBody Customer customer) {
        return service.saveCustomer(customer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomerById(@PathVariable int id) {
        return service.deleteCustomerById(id);
    }

}
