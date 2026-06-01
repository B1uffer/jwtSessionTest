package com.example.demo.coffee.dto;

import com.example.demo.coffee.entity.Coffee;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CoffeeResponseDto {
    private long coffeeId;

    private String korName;

    private String engName;

    private int price;

    private String coffeeCode;

    private Coffee.CoffeeStatus coffeeStatus;
}
