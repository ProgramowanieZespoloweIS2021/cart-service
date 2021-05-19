package com.pz.cartservice.carts.adapters.persistence;

import com.pz.cartservice.carts.adapters.persistence.orm.ShoppingCartItemOrm;
import com.pz.cartservice.carts.adapters.persistence.orm.ShoppingCartOrm;
import com.pz.cartservice.carts.adapters.persistence.orm.ShoppingCartOrmRepository;
import com.pz.cartservice.carts.domain.entity.ShoppingCart;
import com.pz.cartservice.carts.domain.entity.ShoppingCartItem;
import com.pz.cartservice.carts.domain.repository.ShoppingCartRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class ShoppingCartJpaRepository implements ShoppingCartRepository {

    private final ShoppingCartOrmRepository shoppingCartOrmRepository;

    public ShoppingCartJpaRepository(ShoppingCartOrmRepository shoppingCartOrmRepository) {
        this.shoppingCartOrmRepository = shoppingCartOrmRepository;
    }

    @Override
    public Long add(ShoppingCart shoppingCart) {
        ShoppingCartOrm shoppingCartOrm = new ShoppingCartOrm();
        shoppingCartOrm.setId(shoppingCart.getId());
        List<ShoppingCartItemOrm> shoppingCartItemsOrm = shoppingCart.getItems()
                .stream().map(item -> ShoppingCartItemOrm.fromEntity(item, shoppingCartOrm))
                .collect(Collectors.toList());
        shoppingCartOrm.setItems(shoppingCartItemsOrm);
        return shoppingCartOrmRepository.saveAndFlush(shoppingCartOrm).getId();
    }


    @Override
    public Optional<ShoppingCart> get(Long cartId) {
        Optional<ShoppingCartOrm> shoppingCartOrm = shoppingCartOrmRepository.findById(cartId);
        if (shoppingCartOrm.isEmpty()) {
            return Optional.empty();
        }
        List<ShoppingCartItem> shoppingCartItems = shoppingCartOrm.get().getItems()
                .stream().map(ShoppingCartItemOrm::toEntity)
                .collect(Collectors.toList());
        return Optional.of(new ShoppingCart(shoppingCartOrm.get().getId(), shoppingCartItems));
    }


    @Override
    public void delete(Long cartId) {
        shoppingCartOrmRepository.deleteById(cartId);
    }
}
