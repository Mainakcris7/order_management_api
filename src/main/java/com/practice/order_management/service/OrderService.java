package com.practice.order_management.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.practice.order_management.models.Order;

public interface OrderService {
    // GET methods
    public ResponseEntity<List<Order>> findAllOrders();

    public ResponseEntity<Order> findOrderById(int id);

    public ResponseEntity<List<Order>> findOrdersByNameContains(String pattern);

    public ResponseEntity<List<Order>> findOrdersByPrice(double price);

    public ResponseEntity<List<Order>> findOrdersByPriceBetween(double minPrice, double maxPrice);

    public ResponseEntity<List<Order>> findOrdersByPriceGreaterThanOrEqualTo(double minPrice);

    public ResponseEntity<List<Order>> findOrdersByPriceLessThanOrEqualTo(double maxPrice);

    public ResponseEntity<List<Order>> findOrdersByOrderedAt(LocalDateTime dateTime);

    public ResponseEntity<List<Order>> findOrdersByOrderedBefore(LocalDateTime dateTime);

    public ResponseEntity<List<Order>> findOrdersByOrderedAfter(LocalDateTime dateTime);

    public ResponseEntity<List<Order>> findOrdersByOrderedBetween(LocalDateTime before, LocalDateTime after);

    // POST methods
    public ResponseEntity<Order> saveOrder(Order order);

    // PUT methods
    public ResponseEntity<Order> updateOrder(Order order);

    // DELETE methods
    public ResponseEntity<Order> deleteOrderById(int id);
}
