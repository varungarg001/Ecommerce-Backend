package com.varun.ecommerce.service.cart;

import com.varun.ecommerce.exception.ResourceNotFound;
import com.varun.ecommerce.model.Cart;
import com.varun.ecommerce.model.CartItem;
import com.varun.ecommerce.model.User;
import com.varun.ecommerce.repository.CartItemRepo;
import com.varun.ecommerce.repository.CartRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService{

    private final CartRepo cartRepo;

    private final CartItemRepo cartItemRepo;


    @Override
    public Cart getCart(Long id) {
        Cart cart=cartRepo.findById(id)
                .orElseThrow(()->new ResourceNotFound("Cart Not Found"));

        BigDecimal totalAmount = getTotalPrice(id);
        cart.setTotalAmount(totalAmount);
        return cartRepo.save(cart);

    }

    @Override
    @Transactional
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cartItemRepo.deleteAllByCartId(id);
        cart.getCartItems().clear();
        cartRepo.deleteById(id);

    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = cartRepo.findById(id).orElseThrow(()->new ResourceNotFound("Resource not found"));
        return cart.getCartItems()
                .stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }



    @Override
    public Cart initializeCart(User user){
        return Optional.ofNullable(getCartByUserId(user.getId()))
                .orElseGet(()->{
                    Cart cart=new Cart();
                    cart.setUser(user);
                    return cartRepo.save(cart);
                });
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepo.findByUserId(userId);
    }
}
