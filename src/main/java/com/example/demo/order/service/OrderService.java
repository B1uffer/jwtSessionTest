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

    /**
     * 검증 및 stamp 관리, saveOrder는 private로
     */
    Order findVerifiedOrderId(long orderId);
    void verifyOrder(Order order);

    /**
     * Order를 받아서 stamp 관리하기
     */
    void updateStamp(Order order);
    int calculateStampCount(Order order);
}
