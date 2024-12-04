package com.practice.order_management.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.practice.order_management.models.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class CustomerRepoImpl implements CustomerRepo {

    private EntityManager entityManager;

    public CustomerRepoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Customer> findAllCustomers() {
        TypedQuery<Customer> query = entityManager.createQuery("FROM Customer", Customer.class);
        return query.getResultList();
    }

    @Override
    public Customer findCustomerById(int id) {
        Customer customer = entityManager.find(Customer.class, id);
        return customer;
    }

    @Override
    public List<Customer> findCustomersByNameContains(String pattern) {
        TypedQuery<Customer> query = entityManager.createQuery(
                "SELECT c FROM Customer c WHERE name LIKE CONCAT('%', :pattern, '%')",
                Customer.class);
        query.setParameter("pattern", pattern);
        return query.getResultList();
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        TypedQuery<Customer> query = entityManager.createQuery("FROM Customer WHERE email = :email", Customer.class);
        query.setParameter("email", email);
        List<Customer> customers = query.getResultList();
        return customers.isEmpty() ? null : customers.get(0);
    }

    @Override
    @Transactional
    public Customer saveCustomer(Customer customer) {
        Customer savedCustomer = entityManager.merge(customer);
        return savedCustomer;

    }

    @Override
    @Transactional
    public Customer deleteCustomer(int id) {
        // Find the customer by ID
        Customer customer = entityManager.find(Customer.class, id);
        if (customer == null) {
            return null;
        }
        // Delete the customer
        entityManager.remove(customer);
        return customer;
    }

}
