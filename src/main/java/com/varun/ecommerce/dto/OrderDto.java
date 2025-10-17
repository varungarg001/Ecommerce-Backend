package com.varun.ecommerce.dto;

import com.varun.ecommerce.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long orderId;
    private LocalDate orderDate;
    private BigDecimal totalOrderAmount;
    private OrderStatus orderStatus;
    private List<OrderItemDto> items;
}
