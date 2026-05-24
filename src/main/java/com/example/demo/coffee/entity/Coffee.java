package com.example.demo.coffee.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Coffee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long coffeeId;

    // 한글이름
    @Column(length = 100, nullable = false)
    private String korName;

    // 영문이름
    @Column(length = 100, nullable = false)
    private String engName;

    // 가격
    @Column(nullable = false)
    private Integer price;

    // 커피의 고유한 코드
    @Column(length = 3, nullable = false, unique = true)
    private String coffeeCode;

    // 커피의 상태
    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private CoffeeStatus coffeeStatus = CoffeeStatus.COFFEE_FOR_SALE;

    public enum CoffeeStatus {
        COFFEE_FOR_SALE("커피 판매 중"),
        COFFEE_SOLD_OUT("커피 판매 중지");

        @Getter
        private String status;

        CoffeeStatus(String status) {
            this.status = status;
        }
    }
}
