package com.example.demo.coffee.mapper;

import com.example.demo.coffee.dto.CoffeePatchDto;
import com.example.demo.coffee.dto.CoffeePostDto;
import com.example.demo.coffee.dto.CoffeeResponseDto;
import com.example.demo.coffee.entity.Coffee;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CoffeeMapper {
    Coffee coffeePostToCoffee(CoffeePostDto coffeePostDto);
    Coffee coffeePatchToCoffee(CoffeePatchDto coffeePatchDto);
    CoffeeResponseDto coffeeToCoffeeResponseDto(Coffee coffee);
    List<CoffeeResponseDto> coffeesToCoffeeResponseDto(List<Coffee> coffees);
}
