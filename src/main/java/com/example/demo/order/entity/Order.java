package com.example.demo.order.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "ORDERS")
@Setter
@Getter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderCoffee> orderCoffees = new ArrayList<>();

    // 커피 주문 추가하기
    public void addOrderCoffee(OrderCoffee orderCoffee) {
        this.orderCoffees.add(orderCoffee);
        if(orderCoffee.getOrder() != this) { // orderCoffee가 바라보는 Order가 지금 Order와 다르다면
            orderCoffee.addOrder(this); // 새로운 OrderCoffee에 order를 넣는다
        }
    }

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
