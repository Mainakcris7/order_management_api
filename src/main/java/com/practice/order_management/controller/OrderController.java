package com.practice.order_management.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.practice.order_management.errors.AppException;
import com.practice.order_management.models.Order;
import com.practice.order_management.service.OrderServiceImpl;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
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
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderServiceImpl service;

    @GetMapping("")
    public ResponseEntity<List<Order>> getAllOrders() {
        return service.findAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable int id) {
        return service.findOrderById(id);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Order>> getOrdersByNameContains(@PathVariable String name) {
        return service.findOrdersByNameContains(name);
    }

    @GetMapping("/price/{price}")
    public ResponseEntity<List<Order>> getOrdersByPrice(@PathVariable double price) {
        return service.findOrdersByPrice(price);
    }

    @GetMapping("/price")
    public ResponseEntity<List<Order>> getOrdersByPriceFlexible(
            @RequestParam(value = "gte", required = false) Double minPrice,
            @RequestParam(value = "lte", required = false) Double maxPrice) {
        if (minPrice != null && maxPrice == null) {
            return service.findOrdersByPriceGreaterThanOrEqualTo(minPrice);
        } else if (maxPrice != null && minPrice == null) {
            return service.findOrdersByPriceLessThanOrEqualTo(maxPrice);
        } else if (minPrice != null && maxPrice != null) {
            return service.findOrdersByPriceBetween(minPrice, maxPrice);
        } else {
            throw new AppException(400, "No query parameter ('gte' or 'lte') is specified!");
        }
    }

    @GetMapping("/price/between/{minPrice}/{maxPrice}")
    public ResponseEntity<List<Order>> getOrdersByPriceBetween(@PathVariable double minPrice,
            @PathVariable double maxPrice) {
        return service.findOrdersByPriceBetween(minPrice, maxPrice);
    }

    @GetMapping("/ordered_at/{orderedAt}")
    public ResponseEntity<List<Order>> getOrdersByOrderedAt(@PathVariable LocalDateTime orderedAt) {
        return service.findOrdersByOrderedAt(orderedAt);
    }

    @GetMapping("/ordered_between/{before}/{after}")
    public ResponseEntity<List<Order>> getOrdersByOrderedAtBetween(@PathVariable LocalDateTime before,
            @PathVariable LocalDateTime after) {
        return service.findOrdersByOrderedBetween(before, after);
    }

    @GetMapping("/ordered_at")
    public ResponseEntity<List<Order>> getOrdersByOrderedAtFlexible(
            @RequestParam(value = "before", required = false) LocalDateTime before,
            @RequestParam(value = "after", required = false) LocalDateTime after) {
        if (before != null && after == null) {
            return service.findOrdersByOrderedBefore(before);
        } else if (before == null && after != null) {
            return service.findOrdersByOrderedAfter(after);
        } else if (before != null && after != null) {
            return service.findOrdersByOrderedBetween(before, after);
        } else {
            throw new AppException(400, "No query parameter ('before' or 'after') is specified!");
        }
    }

    @PutMapping("")
    public ResponseEntity<Order> updateOrder(@Valid @RequestBody Order order) {
        return service.updateOrder(order);
    }

    @PostMapping("")
    public ResponseEntity<Order> saveOrder(@Valid @RequestBody Order order) {
        return service.saveOrder(order);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Order> deleteOrderById(@PathVariable int id) {
        return service.deleteOrderById(id);
    }

}
