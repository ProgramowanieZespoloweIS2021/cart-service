package com.pz.cartservice.carts.domain.entity;

import com.pz.cartservice.carts.domain.exception.InvalidItemSpecificationException;

import java.util.Collections;
import java.util.List;

public class ShoppingCart {

    private final Long id;
    private final List<ShoppingCartItem> items;


    public ShoppingCart(Long id, List<ShoppingCartItem> items) {
        this.id = id;
        this.items = items;
    }

    public static ShoppingCart emptyCart() {
        return new ShoppingCart(null, Collections.emptyList());
    }

    public Long getId() {
        return id;
    }

    public List<ShoppingCartItem> getItems() {
        return items;
    }

    public ShoppingCartItem getItem(Long itemId) {
        return items.stream()
                .filter(item -> item.getId().equals(itemId)).findFirst()
                .orElseThrow(() -> new InvalidItemSpecificationException(String.format("Cart with ID %d does not contain item with ID %d", id, itemId)));
    }

    public void addItem(ShoppingCartItem shoppingCartItem) {
        items.add(shoppingCartItem);
    }

    public void removeItem(Long itemId) {
        ShoppingCartItem shoppingCartItem = getItem(itemId);
        items.remove(shoppingCartItem);
    }

    public void updateItem(ShoppingCartItem shoppingCartItem) {
        removeItem(shoppingCartItem.getId());
        addItem(shoppingCartItem);
    }
}
