package com.example.demo.order.dto;

import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class OrderCoffeeDto {
    @Positive
    private long coffeeId;

    @Positive
    private int quantity;
}
