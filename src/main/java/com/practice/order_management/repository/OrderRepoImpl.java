package com.practice.order_management.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.practice.order_management.models.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class OrderRepoImpl implements OrderRepo {

    private EntityManager entityManager;

    public OrderRepoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Order> findAllOrders() {
        TypedQuery<Order> query = entityManager.createQuery("FROM Order", Order.class);
        return query.getResultList();
    }

    @Override
    public Order findOrderById(int id) {
        Order order = entityManager.find(Order.class, id);
        return order;
    }

    @Override
    public List<Order> findOrdersByNameContains(String pattern) {
        TypedQuery<Order> query = entityManager.createQuery("FROM Order WHERE name LIKE CONCAT('%', :pattern, '%')",
                Order.class);
        query.setParameter("pattern", pattern);
        return query.getResultList();
    }

    @Override
    public List<Order> findOrdersByPrice(double price) {
        TypedQuery<Order> query = entityManager.createQuery("FROM Order WHERE price = :price",
                Order.class);
        query.setParameter("price", price);
        return query.getResultList();
    }

    @Override
    public List<Order> findOrdersByPriceBetween(double minPrice, double maxPrice) {
        TypedQuery<Order> query = entityManager.createQuery("FROM Order WHERE price BETWEEN :minPrice AND :maxPrice",
                Order.class);
        query.setParameter("minPrice", minPrice);
        query.setParameter("maxPrice", maxPrice);
        return query.getResultList();
    }

    @Override
    public List<Order> findOrdersByPriceGreaterThanOrEqualTo(double minPrice) {
        TypedQuery<Order> query = entityManager.createQuery("FROM Order WHERE price >= :minPrice",
                Order.class);
        query.setParameter("minPrice", minPrice);
        return query.getResultList();
    }

    @Override
    public List<Order> findOrdersByPriceLessThanOrEqualTo(double maxPrice) {
        TypedQuery<Order> query = entityManager.createQuery("FROM Order WHERE price <= :maxPrice",
                Order.class);
        query.setParameter("maxPrice", maxPrice);
        return query.getResultList();
    }

    @Override
    public List<Order> findOrdersByOrderedAt(LocalDateTime dateTime) {
        TypedQuery<Order> query = entityManager.createQuery("FROM Order WHERE orderedAt = :dateTime",
                Order.class);
        query.setParameter("dateTime", dateTime);
        return query.getResultList();
    }

    @Override
    public List<Order> findOrdersByOrderedBefore(LocalDateTime dateTime) {
        TypedQuery<Order> query = entityManager.createQuery("FROM Order WHERE orderedAt <= :dateTime",
                Order.class);
        query.setParameter("dateTime", dateTime);
        return query.getResultList();
    }

    @Override
    public List<Order> findOrdersByOrderedAfter(LocalDateTime dateTime) {
        TypedQuery<Order> query = entityManager.createQuery("FROM Order WHERE orderedAt >= :dateTime",
                Order.class);
        query.setParameter("dateTime", dateTime);
        return query.getResultList();
    }

    @Override
    public List<Order> findOrdersByOrderedBetween(LocalDateTime before, LocalDateTime after) {
        TypedQuery<Order> query = entityManager.createQuery("FROM Order WHERE orderedAt BETWEEN  :before AND :after",
                Order.class);
        query.setParameter("before", before);
        query.setParameter("after", after);
        return query.getResultList();
    }

    @Override
    @Transactional
    public Order saveOrder(Order order) {
        Order savedOrder = entityManager.merge(order);
        return savedOrder;
    }

    @Override
    @Transactional
    public Order deleteOrderById(int id) {
        // Find the order by ID
        Order order = this.findOrderById(id);
        if (order == null) {
            return null;
        }
        // Delete the order
        entityManager.remove(order);
        return order;
    }

}
