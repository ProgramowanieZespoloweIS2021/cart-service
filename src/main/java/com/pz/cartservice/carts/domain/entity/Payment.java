package com.pz.cartservice.carts.domain.entity;

import java.math.BigDecimal;
import java.util.List;

public class Payment {

    private final Long userId;
    private final BigDecimal price;
    private final String status;
    private final List<String> offerTitles;

    public Payment(Long userId, BigDecimal price, String status, List<String> offerTitles) {
        this.userId = userId;
        this.price = price;
        this.status = status;
        this.offerTitles = offerTitles;
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

    public List<String> getOfferTitles() {
        return offerTitles;
    }
}
