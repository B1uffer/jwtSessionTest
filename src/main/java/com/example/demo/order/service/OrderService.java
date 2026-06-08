package com.example.demo.order.service;

import com.example.demo.order.entity.Order;
import org.springframework.data.domain.Page;

public interface OrderService {
    Order createOrder(Order order);
    Order findOrder(long orderId);
    Page<Order> findOrders(int page, int size);
    Order updateOrder(Order order);
    void deleteOrder(long orderId);

    void cancelOrder(long orderId);
}
