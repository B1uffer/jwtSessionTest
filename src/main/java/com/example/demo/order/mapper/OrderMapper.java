package com.example.demo.order.mapper;

import com.example.demo.coffee.entity.Coffee;
import com.example.demo.member.entity.Member;
import com.example.demo.order.dto.OrderPatchDto;
import com.example.demo.order.dto.OrderPostDto;
import com.example.demo.order.dto.OrderResponseDto;
import com.example.demo.order.entity.Order;
import com.example.demo.order.entity.OrderCoffee;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order orderPathDtoToOrder(OrderPatchDto orderPatchDto);
    List<OrderResponseDto> orderToOrderResponseDtoList(List<Order> orderList);

    /**
     * OrderPostDto를 받아서 Order로 바꾸는 Mapper
     */
    default Order orderPostDtoToOrder(OrderPostDto orderPostDto) {
        Order order = new Order();
        Member member = new Member();
        member.setMemberId(order.getOrderId());

        List<OrderCoffee> orderCoffees = orderPostDto.getOrderCoffees().stream()
                .map(orderCoffeeDto -> {
                    OrderCoffee orderCoffee = new OrderCoffee();
                    Coffee coffee = new Coffee();
                    coffee.setCoffeeId(orderCoffeeDto.getCoffeeId());
                    orderCoffee.setOrder(order);
                    orderCoffee.setQuantity(orderCoffeeDto.getQuantity());
                    orderCoffee.setCoffee(coffee);
                    return orderCoffee;
                }).collect(Collectors.toList()); // toList();

        order.setMember(member);
        order.setOrderCoffees(orderCoffees);

        return order;
    }

    /**
     * List<OrderCoffee> 를 받아서 List<OrderResponseDto>로 반환하는 mapper
     */
}
