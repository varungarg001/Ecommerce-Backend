package com.varun.ecommerce.service.order;

import com.varun.ecommerce.dto.OrderDto;
import com.varun.ecommerce.enums.OrderStatus;
import com.varun.ecommerce.exception.ResourceNotFound;
import com.varun.ecommerce.model.Cart;
import com.varun.ecommerce.model.Order;
import com.varun.ecommerce.model.OrderItem;
import com.varun.ecommerce.model.Product;
import com.varun.ecommerce.repository.OrderRepo;
import com.varun.ecommerce.repository.ProductRepo;
import com.varun.ecommerce.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{

    private final OrderRepo orderRepo;

    private final ProductRepo productRepo;

    private final CartService cartService;

    private final ModelMapper modelMapper;

    @Override
    public Order placeOrder(Long userId) {
        Cart cart=cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItems = createOrderItems(order, cart);
        order.setOrderItems(new HashSet<>(orderItems));
        order.setTotalOrderAmount(calculateTotalAmount(orderItems));
        Order saveOrder = orderRepo.save(order);

        cartService.clearCart(cart.getId());

        return saveOrder;

    }

    private Order createOrder(Cart cart){
        Order order=new Order();

        order.setUser(cart.getUser());

        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;

    }

    private List<OrderItem> createOrderItems(Order order, Cart cart){
        return cart.getCartItems()
                .stream()
                .map(cartItem->{
                    Product product=cartItem.getProduct();
                    product.setInventory(product.getInventory()-cartItem.getQuantity());
                    productRepo.save(product);
                    return new OrderItem(
                            order,
                            product,
                            cartItem.getQuantity(),
                            cartItem.getUnitPrice()

                    );
                }).toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem>orderItems){
        return orderItems
                .stream()
                .map(item->item.getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFound("Order not found"));

        return getOrderDto(order);
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId){
       return  orderRepo.findByUserId(userId).stream().map(this::getOrderDto).toList();
    }

    private OrderDto getOrderDto(Order order){
        return modelMapper.map(order,OrderDto.class);
    }
}
