package com.pz.cartservice.carts.domain.entity;

import java.math.BigDecimal;

public class Payment {

    private final Long userId;
    private final BigDecimal price;
    private final String status;

    public Payment(Long userId, BigDecimal price, String status) {
        this.userId = userId;
        this.price = price;
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }
}
