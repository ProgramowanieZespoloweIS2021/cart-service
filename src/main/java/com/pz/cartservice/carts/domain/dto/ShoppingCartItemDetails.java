package com.pz.cartservice.carts.domain.dto;

import com.pz.cartservice.carts.domain.entity.Offer;
import com.pz.cartservice.carts.domain.entity.ShoppingCartItem;

public class ShoppingCartItemDetails {

    private final Long id;
    private final String description;
    private final Long tierId;
    private final Offer offer;

    public ShoppingCartItemDetails(Long id, String description, Long tierId, Offer offer) {
        this.id = id;
        this.description = description;
        this.tierId = tierId;
        this.offer = offer;
    }

    public static ShoppingCartItemDetails fromShoppingCartItem(ShoppingCartItem shoppingCartItem, Offer offer) {
        return new ShoppingCartItemDetails(
                shoppingCartItem.getId(),
                shoppingCartItem.getDescription(),
                shoppingCartItem.getTierId(),
                offer);
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Long getTierId() {
        return tierId;
    }

    public Offer getOffer() {
        return offer;
    }
}
