package com.pz.cartservice.carts.domain.repository;

import com.pz.cartservice.carts.adapters.api.ShoppingCartItemRequest;
import com.pz.cartservice.carts.domain.entity.ShoppingCart;
import com.pz.cartservice.carts.domain.entity.ShoppingCartItem;

import java.util.Optional;

public interface ShoppingCartRepository {

    Long createCart();

    void deleteCart(Long cartId);

    Optional<ShoppingCart> getCart(Long cartId);

    Optional<ShoppingCartItem> getItem(Long itemId);

    Long addItemToCart(Long cartId, ShoppingCartItemRequest item);

    void editItem(Long itemId, ShoppingCartItemRequest itemUpdate);

    void deleteItem(Long itemId);
}
