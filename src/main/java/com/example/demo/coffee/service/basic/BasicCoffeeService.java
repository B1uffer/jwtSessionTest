package com.example.demo.coffee.service.basic;

import com.example.demo.coffee.entity.Coffee;
import com.example.demo.coffee.repository.CoffeeRepository;
import com.example.demo.coffee.service.CoffeeService;
import com.example.demo.exception.BusinessLogicException;
import com.example.demo.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class BasicCoffeeService implements CoffeeService {
    private final CoffeeRepository coffeeRepository;

    public BasicCoffeeService(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    @Override
    public Coffee createCoffee(Coffee coffee) {
        // 대문자로 만듬
        String bigCoffeeCode = coffee.getCoffeeCode().toUpperCase();
        // 검증
        verifyExistsCoffeeUseCode(bigCoffeeCode);
        Coffee saveCoffee = coffeeRepository.save(coffee);
        return saveCoffee;
    }

    @Override
    public Coffee findCoffee(long coffeeId) {
        // 검증
        Coffee verifiedCoffee = findVerifiedCoffeeUseId(coffeeId);
        return verifiedCoffee;
    }

    @Override
    public Page<Coffee> findCoffees(int page, int size) {
        Page<Coffee> coffee = coffeeRepository.findAll(PageRequest.of(
                page,
                size,
                Sort.by("coffeeId").descending())
        );
        return coffee;
    }

    @Override
    public Coffee updateCoffee(Coffee coffee) {
        Coffee findCoffee = findVerifiedCoffeeUseId(coffee.getCoffeeId());
        Optional.ofNullable(coffee.getKorName())
                .ifPresent(korName -> findCoffee.setKorName(korName));
        Optional.ofNullable(coffee.getEngName())
                        .ifPresent(engName -> findCoffee.setEngName(engName));
        Optional.ofNullable(coffee.getPrice())
                        .ifPresent(price -> findCoffee.setPrice(price));
        Optional.ofNullable(coffee.getCoffeeStatus())
                        .ifPresent(coffeeStatus -> findCoffee.setCoffeeStatus(coffeeStatus));
        Optional.ofNullable(coffee.getCoffeeCode())
                .ifPresent(coffeeCode -> findCoffee.setCoffeeCode(coffeeCode));
        coffeeRepository.save(findCoffee);
        return findCoffee;
    }

    @Override
    public void deleteCoffee(long coffeeId) {

    }

    @Override
    public void verifyExistsCoffeeUseCode(String coffeeCode) {
        Optional<Coffee> coffee = coffeeRepository.findByCoffeeCode(coffeeCode);
        if (coffee.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.COFFEE_CODE_EXISTS);
        }
    }

    @Override
    public Coffee findVerifiedCoffeeUseId(long coffeeId) {
        Optional<Coffee> coffee = coffeeRepository.findById(coffeeId);
        Coffee findCoffee = coffee.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COFFEE_NOT_FOUND));
        return findCoffee;
    }
}
