package com.pz.cartservice.carts.adapters.api;

import com.pz.cartservice.carts.domain.entity.ShoppingCart;

import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCartResponse {

    private final Long id;
    private final List<ShoppingCartItemResponse> items;

    private ShoppingCartResponse(Long id, List<ShoppingCartItemResponse> items) {
        this.id = id;
        this.items = items;
    }

    public static ShoppingCartResponse fromEntity(ShoppingCart shoppingCart) {
        List<ShoppingCartItemResponse> items = shoppingCart.getItems()
                .stream().map(ShoppingCartItemResponse::fromEntity).collect(Collectors.toList());
        return new ShoppingCartResponse(shoppingCart.getId(), items);
    }

    public Long getId() {
        return id;
    }

    public List<ShoppingCartItemResponse> getItems() {
        return items;
    }
}
