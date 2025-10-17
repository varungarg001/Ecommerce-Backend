package com.varun.ecommerce.repository;

import com.varun.ecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order,Long> {
    List<Order> findByUserId(Long userId);
}
