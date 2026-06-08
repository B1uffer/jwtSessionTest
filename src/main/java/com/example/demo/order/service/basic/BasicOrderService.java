package com.example.demo.order.service.basic;

import com.example.demo.coffee.service.CoffeeService;
import com.example.demo.helper.StampCalculator;
import com.example.demo.member.entity.Member;
import com.example.demo.member.service.MemberService;
import com.example.demo.order.entity.Order;
import com.example.demo.order.mapper.OrderMapper;
import com.example.demo.order.repository.OrderCoffeeRepository;
import com.example.demo.order.repository.OrderRepository;
import com.example.demo.order.service.OrderService;
import com.example.demo.stamp.Stamp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
public class BasicOrderService implements OrderService {
    private final OrderRepository orderRepository;
    private final MemberService memberService;
    private final CoffeeService coffeeService;

    BasicOrderService(OrderRepository orderRepository,
                      MemberService memberService,
                      CoffeeService coffeeService) {
        this.orderRepository = orderRepository;
        this.memberService = memberService;
        this.coffeeService = coffeeService;
    }

    @Override
    public Order createOrder(Order order) {
        verifyOrder(order);
        Order saveOrder = saveOrder(order);
        return saveOrder;
    }

    @Override
    public Order findOrder(long orderId) {
        Order order = findVerifiedOrderId(orderId);
        return order;
    }

    @Override
    public Page<Order> findOrders(int page, int size) {
        Page<Order> pageOrders = orderRepository.findAll(
                PageRequest.of(page, size, Sort.by("orderId")
                        .descending()
                )
        );
        return pageOrders;
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
        Member member = memberService.findMember(order.getMember().getMemberId());
        // calculateStampCount로 order 안에 있는 orderCoffee들의 stamp를 합함
        int calculate = calculateStampCount(order);

        // member에서 stamp를 가져오고
        Stamp stamp = member.getStamp();

        // stamp에 있는 stampCount와 기존에 있던 stamp를 합한 것들을 서로 더한다
        stamp.setStampCount(StampCalculator.calculateStampCount(
                stamp.getStampCount(), calculate));

        member.setStamp(stamp);
        memberService.updateMember(member);
    }

    @Override
    public int calculateStampCount(Order order) {
        return order.getOrderCoffees().stream()
                .map(orderCoffee -> orderCoffee.getQuantity())
                .mapToInt(quantity -> quantity)
                .sum();
    }

    private Order saveOrder(Order order) {
        return orderRepository.save(order);
    }
}
