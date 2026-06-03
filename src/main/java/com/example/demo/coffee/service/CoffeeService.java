package com.example.demo.coffee.service;

import com.example.demo.coffee.entity.Coffee;
import org.springframework.data.domain.Page;

public interface CoffeeService {
    Coffee createCoffee(Coffee coffee);
    Coffee findCoffee(long coffeeId);
    Page<Coffee> findCoffees(int page, int size);
    Coffee updateCoffee(Coffee coffee);
    void deleteCoffee(long coffeeId);

    void verifyExistsCoffeeUseCode(String coffeeCode);
    Coffee findVerifiedCoffeeUseId(long coffeeId);
}
