package com.pz.cartservice.carts.adapters.api.response;

import com.pz.cartservice.carts.domain.dto.ShoppingCartDetails;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCartResponse {

    private final Long id;
    private final BigDecimal totalPrice;
    private final List<ShoppingCartItemResponse> items;

    private ShoppingCartResponse(Long id, BigDecimal totalPrice, List<ShoppingCartItemResponse> items) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.items = items;
    }

    public static ShoppingCartResponse fromShoppingCartDetails(ShoppingCartDetails shoppingCartDetails) {
        List<ShoppingCartItemResponse> items = shoppingCartDetails.getItems()
                .stream().map(ShoppingCartItemResponse::fromShoppingCartItemDetails)
                .collect(Collectors.toList());
        return new ShoppingCartResponse(shoppingCartDetails.getId(), shoppingCartDetails.getTotalPrice(), items);
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public List<ShoppingCartItemResponse> getItems() {
        return items;
    }
}
