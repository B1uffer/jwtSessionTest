package com.example.demo.coffee.controller;

import com.example.demo.coffee.dto.CoffeePostDto;
import com.example.demo.coffee.entity.Coffee;
import com.example.demo.coffee.mapper.CoffeeMapper;
import com.example.demo.coffee.repository.CoffeeRepository;
import com.example.demo.coffee.service.CoffeeService;
import com.example.demo.dto.SingleResponseDto;
import com.example.demo.utils.UriCreator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/v11/coffees")
@Validated
public class CoffeeController {
    private final String COFFFEE_DEFAULT_URL = "/v11/coffees";
    private final CoffeeService coffeeService;
    private final CoffeeMapper coffeeMapper;

    public CoffeeController(CoffeeService coffeeService, CoffeeMapper coffeeMapper, CoffeeRepository coffeeRepository) {
        this.coffeeService = coffeeService;
        this.coffeeMapper = coffeeMapper;
    }

    @PostMapping
    public ResponseEntity postCoffee(@Valid @RequestBody CoffeePostDto coffeePostDto) {
        Coffee coffee = coffeeService.createCoffee(coffeeMapper.coffeePostToCoffee(coffeePostDto));
        URI location = UriCreator.createUri(COFFFEE_DEFAULT_URL, coffee.getCoffeeId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{coffee_id}")
    public ResponseEntity getCoffee(@PathVariable("coffee-id") @Positive long coffeeId) {
        Coffee coffee = coffeeService.findCoffee(coffeeId);
        return new ResponseEntity<>(
                new SingleResponseDto<>(coffeeMapper.coffeeToCoffeeResponseDto(coffee)), HttpStatus.OK);
    }
}
