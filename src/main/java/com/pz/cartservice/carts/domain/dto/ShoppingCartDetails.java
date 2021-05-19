package com.pz.cartservice.carts.domain.dto;


import java.math.BigDecimal;
import java.util.List;

public class ShoppingCartDetails {

    private final Long id;
    private final BigDecimal totalPrice;
    private final List<ShoppingCartItemDetails> items;

    public ShoppingCartDetails(Long id, BigDecimal totalPrice, List<ShoppingCartItemDetails> items) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public List<ShoppingCartItemDetails> getItems() {
        return items;
    }
}
