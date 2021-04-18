package com.pz.cartservice.carts.dto;

import javax.validation.constraints.NotNull;

public class ShoppingCartItemPostDTO {

    @NotNull(message = "Providing offer is mandatory.")
    private final Long offerId;

    @NotNull(message = "Providing tier is mandatory.")
    private final Long tierId;

    private final String description;

    public ShoppingCartItemPostDTO(Long offerId, Long tierId, String description) {
        this.offerId = offerId;
        this.tierId = tierId;
        this.description = description;
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
