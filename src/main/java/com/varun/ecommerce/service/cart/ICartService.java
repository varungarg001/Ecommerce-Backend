package com.varun.ecommerce.service.cart;

import com.varun.ecommerce.model.Cart;

import java.math.BigDecimal;

public interface ICartService {

    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Long initializeCart();
}
