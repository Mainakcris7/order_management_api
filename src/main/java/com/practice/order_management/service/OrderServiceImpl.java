package com.practice.order_management.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.practice.order_management.models.Customer;
import com.practice.order_management.models.Order;
import com.practice.order_management.repository.CustomerRepoImpl;
import com.practice.order_management.repository.OrderRepoImpl;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepoImpl repo;

    @Autowired
    private CustomerRepoImpl customerRepo;

    public OrderServiceImpl(OrderRepoImpl repo) {
        this.repo = repo;
    }

    @Override
    public ResponseEntity<List<Order>> findAllOrders() {
        List<Order> orders = repo.findAllOrders();
        if (orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @Override
    public ResponseEntity<Order> findOrderById(int id) {
        Order order = repo.findOrderById(id);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @Override
    public ResponseEntity<List<Order>> findOrdersByNameContains(String pattern) {
        List<Order> orders = repo.findOrdersByNameContains(pattern);
        if (orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @Override
    public ResponseEntity<List<Order>> findOrdersByPrice(double price) {
        List<Order> orders = repo.findOrdersByPrice(price);
        if (orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @Override
    public ResponseEntity<List<Order>> findOrdersByPriceBetween(double minPrice, double maxPrice) {
        List<Order> orders = repo.findOrdersByPriceBetween(minPrice, maxPrice);
        if (orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @Override
    public ResponseEntity<List<Order>> findOrdersByPriceGreaterThanOrEqualTo(double minPrice) {
        List<Order> orders = repo.findOrdersByPriceGreaterThanOrEqualTo(minPrice);
        if (orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @Override
    public ResponseEntity<List<Order>> findOrdersByPriceLessThanOrEqualTo(double maxPrice) {
        List<Order> orders = repo.findOrdersByPriceLessThanOrEqualTo(maxPrice);
        if (orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @Override
    public ResponseEntity<List<Order>> findOrdersByOrderedAt(LocalDateTime dateTime) {
        List<Order> orders = repo.findOrdersByOrderedAt(dateTime);
        if (orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @Override
    public ResponseEntity<List<Order>> findOrdersByOrderedBefore(LocalDateTime dateTime) {
        List<Order> orders = repo.findOrdersByOrderedBefore(dateTime);
        if (orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @Override
    public ResponseEntity<List<Order>> findOrdersByOrderedAfter(LocalDateTime dateTime) {
        List<Order> orders = repo.findOrdersByOrderedAfter(dateTime);
        if (orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @Override
    public ResponseEntity<List<Order>> findOrdersByOrderedBetween(LocalDateTime before, LocalDateTime after) {
        List<Order> orders = repo.findOrdersByOrderedBetween(before, after);
        if (orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @Override
    public ResponseEntity<Order> saveOrder(Order order) {
        order.setId(0); // To ensure JPA treats it as a new object
        order.setOrderedAt(LocalDateTime.now()); // Setting the current time stamp
        order.setCustomer(order.getCustomer()); // Associating the customer details with the order
        Order savedOrder = repo.saveOrder(order);
        if (savedOrder == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    // The reason I am separating the saveOrder and updateOrder is because I don't
    // want the user to change the customer details, associated with the
    // order, when updating the order
    @Override
    public ResponseEntity<Order> updateOrder(Order order) {
        if (order.getOrderedAt() == null) {
            order.setOrderedAt(LocalDateTime.now());
        }
        // Fetching the previous version of the order that is to be updated
        Order prevOrder = repo.findOrderById(order.getId());
        if (prevOrder == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        // Fetching the associated customer with the order
        Customer associatedCustomer = customerRepo.findCustomerById(prevOrder.getCustomer().getId());
        // Setting the same customer, to not manipulate the customer details for
        // security concerns
        order.setCustomer(associatedCustomer);
        // Updating the order
        Order updatedOrder = repo.saveOrder(order);
        if (updatedOrder == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(updatedOrder);
    }

    @Override
    public ResponseEntity<Order> deleteOrderById(int id) {
        Order deletedOrder = repo.deleteOrderById(id);
        if (deletedOrder == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(deletedOrder);
    }

}
