package com.varun.ecommerce.service.cart;

import com.varun.ecommerce.model.Cart;
import com.varun.ecommerce.model.User;

import java.math.BigDecimal;

public interface ICartService {

    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Cart initializeCart(User user);

    Cart getCartByUserId(Long userId);

}
