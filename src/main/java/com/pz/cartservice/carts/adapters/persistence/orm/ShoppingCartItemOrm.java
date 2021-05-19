package com.pz.cartservice.carts.adapters.persistence.orm;

import com.pz.cartservice.carts.domain.entity.ShoppingCartItem;

import javax.persistence.*;

@Entity
@Table(name = "shopping_cart_item")
public class ShoppingCartItemOrm {

    private Long id;
    private Long offerId;
    private Long tierId;
    private String description;
    private ShoppingCartOrm cart;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public Long getTierId() {
        return tierId;
    }

    public void setTierId(Long tierId) {
        this.tierId = tierId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    public ShoppingCartOrm getCart() {
        return cart;
    }

    public void setCart(ShoppingCartOrm cart) {
        this.cart = cart;
    }


    public static ShoppingCartItem toEntity(ShoppingCartItemOrm shoppingCartItemOrm) {
        return new ShoppingCartItem(
                shoppingCartItemOrm.getId(),
                shoppingCartItemOrm.getDescription(),
                shoppingCartItemOrm.getOfferId(),
                shoppingCartItemOrm.getTierId());
    }

    public static ShoppingCartItemOrm fromEntity(ShoppingCartItem shoppingCartItem, ShoppingCartOrm shoppingCartOrm) {
        ShoppingCartItemOrm shoppingCartItemOrm = new ShoppingCartItemOrm();
        shoppingCartItemOrm.setId(shoppingCartItem.getId());
        shoppingCartItemOrm.setOfferId(shoppingCartItem.getOfferId());
        shoppingCartItemOrm.setTierId(shoppingCartItem.getTierId());
        shoppingCartItemOrm.setDescription(shoppingCartItem.getDescription());
        shoppingCartItemOrm.setCart(shoppingCartOrm);
        return shoppingCartItemOrm;
    }
}
