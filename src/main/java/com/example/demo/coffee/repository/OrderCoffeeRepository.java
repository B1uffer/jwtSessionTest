package com.example.demo.coffee.repository;

import com.example.demo.coffee.entity.OrderCoffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderCoffeeRepository extends JpaRepository<OrderCoffee, Long> {
}
