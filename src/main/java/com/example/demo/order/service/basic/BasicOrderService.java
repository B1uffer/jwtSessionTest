package com.example.demo.order.service.basic;

import com.example.demo.member.repository.MemberRepository;
import com.example.demo.member.service.MemberService;
import com.example.demo.order.entity.Order;
import com.example.demo.order.mapper.OrderMapper;
import com.example.demo.order.repository.OrderCoffeeRepository;
import com.example.demo.order.repository.OrderRepository;
import com.example.demo.order.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BasicOrderService implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderCoffeeRepository orderCoffeeRepository;
    private final MemberService memberService;

    BasicOrderService(OrderRepository orderRepository, OrderMapper orderMapper, OrderCoffeeRepository orderCoffeeRepository, MemberService memberService) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.orderCoffeeRepository = orderCoffeeRepository;
        this.memberService = memberService;
    }

    @Override
    public Order createOrder(Order order) {

        return null;
    }

    @Override
    public Order findOrder(long orderId) {
        return null;
    }

    @Override
    public Page<Order> findOrders(int page, int size) {
        return null;
    }

    @Override
    public Order updateOrder(Order order) {
        return null;
    }

    @Override
    public void deleteOrder(long orderId) {

    }

    @Override
    public void cancelOrder(long orderId) {

    }

    @Override
    public Order findVerifiedOrderId(long orderId) {
        return null;
    }

    @Override
    public void verifyOrder(Order order) {

    }

    @Override
    public void updateStamp(Order order) {

    }

    @Override
    public int calculateStampCount(Order order) {
        return 0;
    }

    @Override
    public void saveOrder(Order order) {

    }
}
