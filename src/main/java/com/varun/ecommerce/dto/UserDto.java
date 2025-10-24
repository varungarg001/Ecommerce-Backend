package com.varun.ecommerce.dto;

import com.varun.ecommerce.model.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<OrderDto> orders;
    private CartDto cart;
}
