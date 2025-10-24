package com.varun.ecommerce.service.order;

import com.varun.ecommerce.dto.OrderDto;
import com.varun.ecommerce.model.Order;

import java.util.List;

public interface IOrderService {

    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);

    OrderDto getOrderDto(Order order);
}
