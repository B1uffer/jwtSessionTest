package com.example.demo.order.repository;

import com.example.demo.order.entity.OrderCoffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderCoffeeRepository extends JpaRepository<OrderCoffee, Long> {
}
