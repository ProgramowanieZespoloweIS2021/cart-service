package com.pz.cartservice.carts.persistence;

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
    public ShoppingCartOrm getCart() {
        return cart;
    }

    public void setCart(ShoppingCartOrm cart) {
        this.cart = cart;
    }
}
