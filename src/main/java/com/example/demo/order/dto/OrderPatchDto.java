package com.example.demo.order.dto;

import com.example.demo.order.entity.Order;
import lombok.Getter;

@Getter
public class OrderPatchDto {
    private long orderId;
    private Order.OrderStauts orderStatus;

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
}
