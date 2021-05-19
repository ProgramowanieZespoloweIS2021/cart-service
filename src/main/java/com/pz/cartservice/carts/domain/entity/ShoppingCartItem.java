package com.pz.cartservice.carts.domain.entity;

import java.util.Objects;

public class ShoppingCartItem {

    private final Long id;
    private final String description;
    private final Long offerId;
    private final Long tierId;

    public ShoppingCartItem(Long id, String description, Long offerId, Long tierId) {
        this.id = id;
        this.description = description;
        this.offerId = offerId;
        this.tierId = tierId;
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

    public Long getTierId() {
        return tierId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCartItem that = (ShoppingCartItem) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
