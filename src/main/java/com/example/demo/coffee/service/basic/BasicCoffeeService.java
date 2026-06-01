package com.example.demo.coffee.service.basic;

import com.example.demo.coffee.entity.Coffee;
import com.example.demo.coffee.repository.CoffeeRepository;
import com.example.demo.coffee.service.CoffeeService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class BasicCoffeeService implements CoffeeService {
    private final CoffeeRepository coffeeRepository;

    public BasicCoffeeService(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    @Override
    public Coffee createCoffee(Coffee coffee) {
        return null;
    }

    @Override
    public Coffee findCoffee(long coffeeId) {
        return null;
    }

    @Override
    public Page<Coffee> findCoffees(int page, int size) {
        return null;
    }

    @Override
    public Coffee updateCoffee(Coffee coffee) {
        return null;
    }

    @Override
    public void deleteCoffee(long coffeeId) {

    }

    @Override
    public void verifyExistsCoffee(String coffeeCode) {

    }

    @Override
    public Coffee findVerifiedCoffee(long coffeeId) {
        return null;
    }
}
