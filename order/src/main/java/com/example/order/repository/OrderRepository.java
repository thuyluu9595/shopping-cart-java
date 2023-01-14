package com.example.order.repository;

import com.example.order.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Long countDistinctByUserId();
//    long countAll();
    List<Order> findAllByUserId(Long userId);
}
