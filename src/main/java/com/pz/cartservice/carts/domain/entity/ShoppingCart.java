package com.pz.cartservice.carts.domain.entity;

import java.util.List;

public class ShoppingCart {

    private final Long id;
    private final List<ShoppingCartItem> items;

    public ShoppingCart(Long id, List<ShoppingCartItem> items) {
        this.id = id;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public List<ShoppingCartItem> getItems() {
        return items;
    }
}
