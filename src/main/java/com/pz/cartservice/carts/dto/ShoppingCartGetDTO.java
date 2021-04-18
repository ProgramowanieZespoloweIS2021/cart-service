package com.pz.cartservice.carts.dto;

import java.util.List;

public class ShoppingCartGetDTO {

    private final Long id;
    private final List<ShoppingCartItemGetDTO> items;

    public ShoppingCartGetDTO(Long id, List<ShoppingCartItemGetDTO> items) {
        this.id = id;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public List<ShoppingCartItemGetDTO> getItems() {
        return items;
    }
}
