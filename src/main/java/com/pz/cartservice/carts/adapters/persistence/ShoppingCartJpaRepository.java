package com.pz.cartservice.carts.adapters.persistence;

import com.pz.cartservice.carts.adapters.api.ShoppingCartItemRequest;
import com.pz.cartservice.carts.adapters.persistence.orm.ShoppingCartItemOrm;
import com.pz.cartservice.carts.adapters.persistence.orm.ShoppingCartItemOrmRepository;
import com.pz.cartservice.carts.adapters.persistence.orm.ShoppingCartOrm;
import com.pz.cartservice.carts.adapters.persistence.orm.ShoppingCartOrmRepository;
import com.pz.cartservice.carts.domain.repository.ShoppingCartRepository;
import com.pz.cartservice.carts.domain.entity.Offer;
import com.pz.cartservice.carts.domain.entity.ShoppingCart;
import com.pz.cartservice.carts.domain.entity.ShoppingCartItem;
import com.pz.cartservice.carts.domain.entity.Tier;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class ShoppingCartJpaRepository implements ShoppingCartRepository {

    private final ShoppingCartOrmRepository shoppingCartOrmRepository;
    private final ShoppingCartItemOrmRepository shoppingCartItemOrmRepository;


    public ShoppingCartJpaRepository(ShoppingCartOrmRepository shoppingCartOrmRepository,
                                     ShoppingCartItemOrmRepository shoppingCartItemOrmRepository) {
        this.shoppingCartOrmRepository = shoppingCartOrmRepository;
        this.shoppingCartItemOrmRepository = shoppingCartItemOrmRepository;
    }


    @Override
    public Long createCart() { // TODO: refactor
        ShoppingCartOrm emptyCart = new ShoppingCartOrm();
        emptyCart.setItems(Collections.emptyList());
        emptyCart = shoppingCartOrmRepository.saveAndFlush(emptyCart);
        return emptyCart.getId();
    }


    @Override
    public void deleteCart(Long cartId) {
        shoppingCartOrmRepository.deleteById(cartId);
    }


    @Override
    public Optional<ShoppingCart> getCart(Long cartId) {
        Optional<ShoppingCartOrm> shoppingCartOrm = shoppingCartOrmRepository.findById(cartId);
        if(shoppingCartOrm.isEmpty()) {
            return Optional.empty();
        }
        List<ShoppingCartItem> shoppingCartItems = shoppingCartOrm.get().getItems().stream().map(this::convertShoppingCartItemOrmToEntity).collect(Collectors.toList());
        return Optional.of(new ShoppingCart(shoppingCartOrm.get().getId(), shoppingCartItems));
    }


    @Override
    public Optional<ShoppingCartItem> getItem(Long itemId) {
        Optional<ShoppingCartItemOrm> shoppingCartItemOrm = shoppingCartItemOrmRepository.findById(itemId);
        if(shoppingCartItemOrm.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(convertShoppingCartItemOrmToEntity(shoppingCartItemOrm.get()));
    }


    @Override
    public Long addItemToCart(Long cartId, ShoppingCartItemRequest item) { // TODO: refactor
        ShoppingCartOrm shoppingCartOrm = shoppingCartOrmRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Invalid cart id.")); // TODO: better exception
        ShoppingCartItemOrm itemOrm = new ShoppingCartItemOrm();
        itemOrm.setOfferId(item.getOfferId());
        itemOrm.setTierId(item.getTierId());
        itemOrm.setDescription(item.getDescription());
        itemOrm.setCart(shoppingCartOrm);
        shoppingCartOrm.getItems().add(itemOrm); // TODO: handle better
        itemOrm = shoppingCartItemOrmRepository.saveAndFlush(itemOrm); // TODO: handle better
        return itemOrm.getId();
    }


    @Override
    public void editItem(Long itemId, ShoppingCartItemRequest itemUpdate) {
        ShoppingCartItemOrm itemOrm = shoppingCartItemOrmRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Invalid item id.")); // TODO: better exception
        itemOrm.setOfferId(itemUpdate.getOfferId());
        itemOrm.setDescription(itemUpdate.getDescription());
        itemOrm.setTierId(itemUpdate.getTierId()); // TODO: is saving needed here???
    }


    @Override
    public void deleteItem(Long itemId) {
        shoppingCartItemOrmRepository.deleteById(itemId);
    }


    private ShoppingCartItem convertShoppingCartItemOrmToEntity(ShoppingCartItemOrm itemOrm) {
        Offer offer = new Offer();
        offer.setId(itemOrm.getOfferId());
        Tier tier = new Tier();
        tier.setId(itemOrm.getTierId());
        return new ShoppingCartItem(itemOrm.getId(), itemOrm.getDescription(), offer, tier);
    }
}
