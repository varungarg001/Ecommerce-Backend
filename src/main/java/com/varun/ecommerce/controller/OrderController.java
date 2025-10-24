package com.varun.ecommerce.controller;

import com.varun.ecommerce.constants.Messages;
import com.varun.ecommerce.dto.OrderDto;
import com.varun.ecommerce.model.Order;
import com.varun.ecommerce.response.ApiResponse;
import com.varun.ecommerce.service.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {

    private final IOrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId){
        try {
            Order order = orderService.placeOrder(userId);
            OrderDto orderDto = orderService.getOrderDto(order);
            return ResponseEntity.ok(new ApiResponse(Messages.SUCCESS.getValue(), orderDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error Occurred! ",null));
        }
    }

    @GetMapping("/orderId/{orderId}/get-order")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId){
        try {
            OrderDto orderDto = orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse(Messages.SUCCESS.getValue(), orderDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/userId/{userId}/get-orders")
    public ResponseEntity<ApiResponse> getOrderByUserId(@PathVariable Long userId){
        try {
            List<OrderDto> userOrders = orderService.getUserOrders(userId);
            return ResponseEntity.ok( new ApiResponse(Messages.SUCCESS.getValue(), userOrders));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error Occurred",null));
        }
    }
}
