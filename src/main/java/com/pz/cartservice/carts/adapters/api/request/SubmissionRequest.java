package com.pz.cartservice.carts.adapters.api.request;

import javax.validation.constraints.NotNull;

public class SubmissionRequest {

    @NotNull(message = "Cart id is mandatory.")
    private final Long cartId;

    @NotNull(message = "Buyer id is mandatory.")
    private final Long buyerId;

    public SubmissionRequest(Long cartId, Long buyerId) {
        this.cartId = cartId;
        this.buyerId = buyerId;
    }

    public Long getCartId() {
        return cartId;
    }

    public Long getBuyerId() {
        return buyerId;
    }
}
