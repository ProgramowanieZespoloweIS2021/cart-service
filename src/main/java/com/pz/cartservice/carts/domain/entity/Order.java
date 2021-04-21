package com.pz.cartservice.carts.domain.entity;

public class Order {

    private final Long buyerId;
    private final Long offerId;
    private final Long tierId;
    private final String description;

    public Order(Long buyerId, Long offerId, Long tierId, String description) {
        this.buyerId = buyerId;
        this.offerId = offerId;
        this.tierId = tierId;
        this.description = description;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public Long getOfferId() {
        return offerId;
    }

    public Long getTierId() {
        return tierId;
    }

    public String getDescription() {
        return description;
    }
}
