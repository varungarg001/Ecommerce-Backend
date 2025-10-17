package com.varun.ecommerce.repository;

import com.varun.ecommerce.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart,Long> {
    Cart findByUserId(Long userId);

}
