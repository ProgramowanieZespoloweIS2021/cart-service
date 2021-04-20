package com.pz.cartservice.carts.adapters.persistence.orm;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "shopping_cart")
public class ShoppingCartOrm {

    private Long id;
    private List<ShoppingCartItemOrm> items;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToMany(
            mappedBy = "cart",
            orphanRemoval = true
    )
    public List<ShoppingCartItemOrm> getItems() {
        return items;
    }

    public void setItems(List<ShoppingCartItemOrm> items) {
        this.items = items;
    }
}
