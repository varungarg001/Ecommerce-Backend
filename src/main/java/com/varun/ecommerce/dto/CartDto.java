package com.varun.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {

    private Long cartId;
    private Set<CartItemDto> items;
    private BigDecimal totalAmount;

}
