package com.practice.order_management.repository;

import java.util.List;

import com.practice.order_management.models.Customer;

public interface CustomerRepo {
    // GET methods
    public List<Customer> findAllCustomers();

    public Customer findCustomerById(int id);

    public Customer findCustomerByEmail(String email);

    public List<Customer> findCustomersByNameContains(String pattern);

    // POST/PUT methods
    public Customer saveCustomer(Customer customer);

    // DELETE methods
    public Customer deleteCustomer(int id);
}
