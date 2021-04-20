package com.pz.cartservice.carts.domain.entity;

public class ShoppingCartItem {

    private final Long id;
    private final String description;
    private final Offer offer;
    private final Tier tier;

    public ShoppingCartItem(Long id, String description, Offer offer, Tier tier) {
        this.id = id;
        this.description = description;
        this.offer = offer;
        this.tier = tier;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Offer getOffer() {
        return offer;
    }

    public Tier getTier() {
        return tier;
    }
}
