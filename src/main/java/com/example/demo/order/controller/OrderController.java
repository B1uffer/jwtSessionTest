package com.example.demo.order.controller;

import com.example.demo.member.service.MemberService;
import com.example.demo.order.mapper.OrderMapper;
import com.example.demo.order.service.OrderService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v11/orders")
@Validated
public class OrderController {
    private final static String ORDER_DEFAULT_URL = "/v11/orders";
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final MemberService memberService;

    public OrderController(OrderService orderService,
                           OrderMapper orderMapper,
                           MemberService memberService) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
        this.memberService = memberService;
    }
}
