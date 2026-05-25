package com.example.demo.order.entity;

import com.example.demo.coffee.entity.Coffee;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class OrderCoffee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderCoffeeId;

    @Column(nullable = false)
    private int quantity;

    /**
     * order의 pk를 외래키로 들고 있음, 이쪽이 다
     * 주문 하나에 커피주문 여러개가 있을 수 있다
     * order는 orderCoffee를 몰라도 되지만, orderCoffee는 order를 알아야한다
     */
    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "COFFEE_ID")
    private Coffee coffee;

    public void addCoffee(Coffee coffee) {
        this.coffee = coffee;
        if(this.coffee.getOrderCoffees().contains(coffee)) { // 커피가 커피주문을 이미 들고있으면
            // 새로운 커피주문을 갖게 해 중복을 피하면서 주문가능하게끔 함
            this.coffee.addOrderCoffee(this);
        }
    }


    public void addOrder(Order order) {
        this.order = order;
        if(this.order.getOrderCoffees().contains(order)) {
            this.order.addOrderCoffee(this);
        }
    }
}
