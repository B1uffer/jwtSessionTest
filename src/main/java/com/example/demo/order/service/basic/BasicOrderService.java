package com.example.demo.order.service.basic;

import com.example.demo.coffee.service.CoffeeService;
import com.example.demo.member.entity.Member;
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

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class BasicOrderService implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderCoffeeRepository orderCoffeeRepository;
    private final MemberService memberService;
    private final CoffeeService coffeeService;

    BasicOrderService(OrderRepository orderRepository,
                      OrderMapper orderMapper,
                      OrderCoffeeRepository orderCoffeeRepository,
                      MemberService memberService,
                      CoffeeService coffeeService) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.orderCoffeeRepository = orderCoffeeRepository;
        this.memberService = memberService;
        this.coffeeService = coffeeService;
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
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("Order not found"));
        return order;
    }

    /**
     * Order를 통해 Member, Coffee가 존재하는지 확인하는 로직
     * 이 주문이 이 사람이 주문했고, 이 커피가 맞는가
     */
    @Override
    public void verifyOrder(Order order) {
        // member가 존재하는지 확인하기
        memberService.findVerifiedMember(order.getMember().getMemberId());

        // 커피가 존재하는지 확인하기
        order.getOrderCoffees().forEach(coffee -> {
           coffeeService.findVerifiedCoffeeUseId(coffee.getCoffee().getCoffeeId());
        });
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
