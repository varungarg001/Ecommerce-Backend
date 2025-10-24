package com.varun.ecommerce.controller;


import com.varun.ecommerce.constants.Messages;
import com.varun.ecommerce.exception.ResourceNotFound;
import com.varun.ecommerce.model.Cart;
import com.varun.ecommerce.model.User;
import com.varun.ecommerce.response.ApiResponse;
import com.varun.ecommerce.service.cart.ICartItemService;
import com.varun.ecommerce.service.cart.ICartService;
import com.varun.ecommerce.service.user.IUserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cart-item")
public class CartItemController {

    private final ICartItemService cartItemService;

    private final ICartService cartService;

    private final IUserService userService;

    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItem(
                                               @RequestParam Long productId,
                                               @RequestParam Integer quantity) {
        try {
            User user = userService.getUserById(1L);
            Cart cart= cartService.initializeCart(user);
            cartItemService.addItemToCart(cart.getId(), productId, quantity);
            return ResponseEntity.ok(new ApiResponse("Add Item Success", null));
        } catch (ResourceNotFound e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("{cartId}/{productId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId,@PathVariable Long productId){
        try {
            cartItemService.removeItemFromCart(cartId,productId);
            return ResponseEntity.ok(new ApiResponse(Messages.SUCCESS.getValue(), null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("{cartId}/{productId}/update-quantity")
    public ResponseEntity<ApiResponse> updateQuantity(@PathVariable Long cartId,
                                                      @PathVariable Long productId, @RequestParam int quantity){
        try {
            cartItemService.updateItemQuantity(cartId,productId,quantity);
            return ResponseEntity.ok(new ApiResponse(Messages.SUCCESS.getValue(), null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
