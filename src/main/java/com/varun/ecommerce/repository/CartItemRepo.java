package com.varun.ecommerce.repository;

import com.varun.ecommerce.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo extends JpaRepository<CartItem,Long> {
    void deleteAllByCartId(Long id);
}

