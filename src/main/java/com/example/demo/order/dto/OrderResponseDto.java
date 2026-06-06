package com.example.demo.order.dto;

import com.example.demo.member.entity.Member;
import com.example.demo.order.entity.Order;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderResponseDto {
    private long orderId;

    @Setter(AccessLevel.NONE)
    private long memberId;
    private Order.OrderStauts orderStatus;
    private List<OrderCoffeeResponseDto> orderCoffees;
    private LocalDateTime createdAt;

    public void setMemberId(Member member) {
        this.memberId = member.getMemberId();
    }
}
