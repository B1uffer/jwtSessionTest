package com.example.demo.coffee.controller;

import com.example.demo.coffee.dto.CoffeePatchDto;
import com.example.demo.coffee.dto.CoffeePostDto;
import com.example.demo.coffee.entity.Coffee;
import com.example.demo.coffee.mapper.CoffeeMapper;
import com.example.demo.coffee.service.CoffeeService;
import com.example.demo.dto.MultiResponseDto;
import com.example.demo.dto.SingleResponseDto;
import com.example.demo.utils.UriCreator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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
        Coffee coffee = coffeeService.createCoffee(coffeeMapper.coffeePostToCoffee(coffeePostDto));
        URI location = UriCreator.createUri(COFFFEE_DEFAULT_URL, coffee.getCoffeeId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{coffee_id}")
    public ResponseEntity getCoffee(@PathVariable("coffee-id")
                                        @Positive long coffeeId) {
        Coffee coffee = coffeeService.findCoffee(coffeeId);
        return new ResponseEntity<>(
                new SingleResponseDto<>(coffeeMapper.coffeeToCoffeeResponseDto(coffee)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAllCoffees(@Positive @RequestParam int page,
                                        @Positive @RequestParam int size) {
        Page<Coffee> pageCoffees = coffeeService.findCoffees(page - 1, size);
        List<Coffee> coffees = pageCoffees.getContent();
        return new ResponseEntity<>(
                new MultiResponseDto<>(coffeeMapper.coffeesToCoffeeResponseDto(coffees),
                        pageCoffees),
                HttpStatus.OK
        );
    }

    // 수정 API
    @PatchMapping("/{coffee-id}")
    public ResponseEntity patchCoffee(
            @PathVariable("coffee-id") @Positive long coffeeId,
            @Valid @RequestBody CoffeePatchDto coffeePatchDto) {

        coffeePatchDto.setCoffeeId(coffeeId);
        Coffee coffee = coffeeService.updateCoffee(coffeeMapper.coffeePatchToCoffee(coffeePatchDto));

        return new ResponseEntity<>(
                new SingleResponseDto<>(coffeeMapper.coffeeToCoffeeResponseDto(coffee)),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{coffee-id}")
    public ResponseEntity deleteCoffee(
            @Positive @PathVariable("coffee-id") long coffeeId
    ) {
        coffeeService.deleteCoffee(coffeeId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
