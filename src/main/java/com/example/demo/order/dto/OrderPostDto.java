package com.example.demo.order.dto;

import com.example.demo.member.entity.Member;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderPostDto {
    @Positive
    private long memberId;

    @Valid
    private List<OrderCoffeeDto> orderCoffees;

    // OrderPostDto의 memberId 필드를 통한 member 얻기
    public Member getMember() {
        Member member = new Member();
        member.setMemberId(memberId);
        return member;
    }
}
