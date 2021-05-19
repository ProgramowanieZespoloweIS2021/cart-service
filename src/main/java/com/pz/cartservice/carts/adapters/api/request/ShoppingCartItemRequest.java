package com.pz.cartservice.carts.adapters.api.request;


import com.pz.cartservice.carts.domain.entity.ShoppingCartItem;

import javax.validation.constraints.NotNull;

public class ShoppingCartItemRequest {

    @NotNull(message = "Providing offer is mandatory.")
    private final Long offerId;

    @NotNull(message = "Providing tier is mandatory.")
    private final Long tierId;

    private final String description;

    public ShoppingCartItemRequest(Long offerId, Long tierId, String description) {
        this.offerId = offerId;
        this.tierId = tierId;
        this.description = description;
    }

    public static ShoppingCartItem toEntity(Long itemId, ShoppingCartItemRequest request) {
        return new ShoppingCartItem(itemId, request.getDescription(), request.getOfferId(), request.getTierId());
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
