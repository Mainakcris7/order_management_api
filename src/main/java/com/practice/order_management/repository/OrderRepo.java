package com.practice.order_management.repository;

import java.time.LocalDateTime;
import java.util.List;
import com.practice.order_management.models.Order;

public interface OrderRepo {
    // GET methods
    public List<Order> findAllOrders();

    public Order findOrderById(int id);

    public List<Order> findOrdersByNameContains(String pattern);

    public List<Order> findOrdersByPrice(double price);

    public List<Order> findOrdersByPriceBetween(double minPrice, double maxPrice);

    public List<Order> findOrdersByPriceGreaterThanOrEqualTo(double minPrice);

    public List<Order> findOrdersByPriceLessThanOrEqualTo(double maxPrice);

    public List<Order> findOrdersByOrderedAt(LocalDateTime dateTime);

    public List<Order> findOrdersByOrderedBefore(LocalDateTime dateTime);

    public List<Order> findOrdersByOrderedAfter(LocalDateTime dateTime);

    public List<Order> findOrdersByOrderedBetween(LocalDateTime before, LocalDateTime after);

    // PUT/POST methods
    public Order saveOrder(Order order);

    // DELETE methods
    public Order deleteOrderById(int id);

}
