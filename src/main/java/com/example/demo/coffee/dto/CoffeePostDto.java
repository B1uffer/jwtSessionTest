package com.example.demo.coffee.dto;

import com.example.demo.coffee.entity.Coffee;
import com.example.demo.validator.NotSpace;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@Getter
public class CoffeePostDto {
    // 커피 코드는 안들어감

    private long coffeeId;

    @NotSpace(message = "커피명(한글)은 공백이 아니어야 합니다.")
    private String korName;

    @Pattern(regexp = "^([A-Za-z])(\\s?[A-Za-z])*$", message = "커피명(영문)은 영문이어야 합니다.")
    private String engName;

    @Range(min = 100, max = 50000)
    private Integer price;

    private Coffee.CoffeeStatus coffeeStatus;

    /**
     * setter
     */
    public void setCoffeeId(long coffeeId) {
        this.coffeeId = coffeeId;
    }
}
