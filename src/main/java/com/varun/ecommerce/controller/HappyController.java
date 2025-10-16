package com.varun.ecommerce.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}")
public class HappyController {

    @GetMapping
    public String happy(){
        return "happy, everythig is Ok";
    }
}
