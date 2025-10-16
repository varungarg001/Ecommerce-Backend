package com.varun.ecommerce.service.cart;

import com.varun.ecommerce.exception.ResourceNotFound;
import com.varun.ecommerce.model.Cart;
import com.varun.ecommerce.model.CartItem;
import com.varun.ecommerce.model.Product;
import com.varun.ecommerce.repository.CartItemRepo;
import com.varun.ecommerce.repository.CartRepo;
import com.varun.ecommerce.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService{

    private final CartItemRepo cartItemRepo;

    private final ProductService productService;

    private final CartService cartService;

    private final CartRepo cartRepo;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        // 1. get the cart
        // 2. get the product
        // 3. check if the product already in the cart
        // 4. if yes then increase the quantity with the requested quantity
        // 5. if no, then initiate new cartItem entry.

        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem = cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(new CartItem());

        if(cartItem.getId()==null){
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }else{
            cartItem.setQuantity(cartItem.getQuantity()+quantity);
        }

        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepo.save(cartItem);
        cartRepo.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem cartItem=getCartItem(cartId,productId);

        cart.removeItem(cartItem);
        cartRepo.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        CartItem cartItem=getCartItem(cartId,productId);

        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice();
        cart.setTotalAmount(cartService.getTotalPrice(cartId));
        cartRepo.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId){
        Cart cart=cartService.getCart(cartId);
        return cart.getCartItems()
                .stream()
                .filter(item->item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(()->new ResourceNotFound("Product not found"));
    }
}
