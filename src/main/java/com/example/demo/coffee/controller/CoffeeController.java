package com.example.demo.coffee.controller;

import com.example.demo.coffee.dto.CoffeePostDto;
import com.example.demo.coffee.mapper.CoffeeMapper;
import com.example.demo.coffee.service.CoffeeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v11/coffees")
@Validated
public class CoffeeController {
    private final String COFFFEE_DEFAULT_URL = "/v11/coffees";
    private final CoffeeService coffeeService;
    private final CoffeeMapper coffeeMapper;

    public CoffeeController(CoffeeService coffeeService, CoffeeMapper coffeeMapper) {
        this.coffeeService = coffeeService;
        this.coffeeMapper = coffeeMapper;
    }

    @PostMapping
    public ResponseEntity postCoffee(@Valid @RequestBody CoffeePostDto coffeePostDto) {
        return null;
    }
}
