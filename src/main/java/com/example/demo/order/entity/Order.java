package com.example.demo.order.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "ORDERS")
@Setter
@Getter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    // 주문 상태
    @Enumerated(EnumType.STRING)
    private OrderStauts orderStatus = OrderStauts.ORDER_REQUEST; // 디폴트

    public enum OrderStauts {
        ORDER_REQUEST(1, "주문 요청"), // 주문
        ORDER_CONFIRM(2, "주문 확인"), // 확인
        ORDER_COMPLETE(3, "주문 완료"), // 완료
        ORDER_CANCEL(4, "주문 취소"); // 취소

        @Getter
        private int stepNumber;

        @Getter
        private String stepDescription;

        OrderStauts(int stepNumber, String stepDescription) {
            this.stepNumber = stepNumber;
            this.stepDescription = stepDescription;
        }
    }
}
