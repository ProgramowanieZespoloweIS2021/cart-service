package com.pz.cartservice.carts.domain.repository;

import com.pz.cartservice.carts.domain.entity.ShoppingCart;

import java.util.Optional;

public interface ShoppingCartRepository {

    Long add(ShoppingCart shoppingCart);

    Optional<ShoppingCart> get(Long cartId);

    void delete(Long cartId);

}
