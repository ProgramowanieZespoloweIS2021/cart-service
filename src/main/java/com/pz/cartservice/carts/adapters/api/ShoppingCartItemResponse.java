package com.pz.cartservice.carts.adapters.api;


import com.pz.cartservice.carts.domain.entity.ShoppingCartItem;

import java.math.BigDecimal;

public class ShoppingCartItemResponse {

    private final Long id;
    private final String description;

    private final Long offerId;
    private final Long offerOwnerId;
    private final String offerTitle;

    private final Long tierId;
    private final String tierTitle;
    private final BigDecimal tierPrice;

    private ShoppingCartItemResponse(Long id, String description, Long offerId, Long offerOwnerId, String offerTitle, Long tierId, String tierTitle, BigDecimal tierPrice) {
        this.id = id;
        this.description = description;
        this.offerId = offerId;
        this.offerOwnerId = offerOwnerId;
        this.offerTitle = offerTitle;
        this.tierId = tierId;
        this.tierTitle = tierTitle;
        this.tierPrice = tierPrice;
    }

    public static ShoppingCartItemResponse fromEntity(ShoppingCartItem shoppingCartItem) {
        return new ShoppingCartItemResponse(
                shoppingCartItem.getId(),
                shoppingCartItem.getDescription(),
                shoppingCartItem.getOffer().getId(),
                shoppingCartItem.getOffer().getOwnerId(),
                shoppingCartItem.getOffer().getTitle(),
                shoppingCartItem.getTier().getId(),
                shoppingCartItem.getTier().getTitle(),
                shoppingCartItem.getTier().getPrice());
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Long getOfferId() {
        return offerId;
    }

    public Long getOfferOwnerId() {
        return offerOwnerId;
    }

    public String getOfferTitle() {
        return offerTitle;
    }

    public Long getTierId() {
        return tierId;
    }

    public String getTierTitle() {
        return tierTitle;
    }

    public BigDecimal getTierPrice() {
        return tierPrice;
    }

}
