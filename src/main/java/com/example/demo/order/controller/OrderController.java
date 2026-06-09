package com.example.demo.order.controller;

import com.example.demo.dto.MultiResponseDto;
import com.example.demo.dto.SingleResponseDto;
import com.example.demo.order.dto.OrderPatchDto;
import com.example.demo.order.dto.OrderPostDto;
import com.example.demo.order.entity.Order;
import com.example.demo.order.mapper.OrderMapper;
import com.example.demo.order.service.OrderService;
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
@RequestMapping("/v11/orders")
@Validated
public class OrderController {
    private final static String ORDER_DEFAULT_URL = "/v11/orders";
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    public OrderController(OrderService orderService,
                           OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @PostMapping
    public ResponseEntity postOrder(@Valid @RequestBody OrderPostDto orderPostDto) {
        Order order = orderService.createOrder(orderMapper.orderPostDtoToOrder(orderPostDto));
        URI location = UriCreator.createUri(ORDER_DEFAULT_URL, order.getOrderId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{order-id}")
    public ResponseEntity getOrder(@PathVariable("order-id") @Positive long orderId) {
        Order order = orderService.findOrder(orderId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(orderMapper.orderToOrderResponseDto(order))
                , HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity getOrders(@Positive @RequestParam int page,
                                    @Positive @RequestParam int size) {
        Page<Order> pageOrders = orderService.findOrders(page - 1, size);
        List<Order> orders = pageOrders.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(orderMapper.orderToOrderResponseDtoList(orders), pageOrders)
                , HttpStatus.OK
        );
    }

    @PatchMapping("/{order-id}")
    public ResponseEntity patchOrder(
            @PathVariable("order-id") @Positive long orderId,
            @Valid @RequestBody OrderPatchDto orderPatchDto
            ) {
        orderPatchDto.setOrderId(orderId);
        Order order = orderService.updateOrder(orderMapper.orderPathDtoToOrder(orderPatchDto));

        return new ResponseEntity<>(
                new SingleResponseDto<>(orderMapper.orderToOrderResponseDto(order))
                , HttpStatus.OK
        );
    }

    @DeleteMapping("/{order-id}")
    public ResponseEntity deleteOrder(
            @PathVariable("order-id") @Positive long orderId) {
        orderService.deleteOrder(orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
