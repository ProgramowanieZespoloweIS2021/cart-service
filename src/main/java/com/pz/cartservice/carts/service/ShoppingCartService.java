package com.pz.cartservice.carts.service;

import com.pz.cartservice.carts.dto.*;
import com.pz.cartservice.carts.externalapi.OfferApiClient;
import com.pz.cartservice.carts.externalapi.OfferExternalApi;
import com.pz.cartservice.carts.externalapi.TierExternalApi;
import com.pz.cartservice.carts.persistence.ShoppingCartItemJpaRepository;
import com.pz.cartservice.carts.persistence.ShoppingCartItemOrm;
import com.pz.cartservice.carts.persistence.ShoppingCartJpaRepository;
import com.pz.cartservice.carts.persistence.ShoppingCartOrm;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ShoppingCartService {

    private final ShoppingCartJpaRepository shoppingCartJpaRepository;
    private final ShoppingCartItemJpaRepository shoppingCartItemJpaRepository;
    private final OfferApiClient offerApiClient;


    public ShoppingCartService(ShoppingCartJpaRepository shoppingCartJpaRepository,
                               ShoppingCartItemJpaRepository shoppingCartItemJpaRepository,
                               OfferApiClient offerApiClient) {
        this.shoppingCartJpaRepository = shoppingCartJpaRepository;
        this.shoppingCartItemJpaRepository = shoppingCartItemJpaRepository;
        this.offerApiClient = offerApiClient;
    }


    public Long createEmptyShoppingCart() {
        ShoppingCartOrm emptyCart = new ShoppingCartOrm();
        emptyCart.setItems(Collections.emptyList());
        emptyCart = shoppingCartJpaRepository.saveAndFlush(emptyCart);
        return emptyCart.getId();
    }


    public ShoppingCartGetDTO getExistingShoppingCart(Long cartId) {
        ShoppingCartOrm shoppingCartOrm = shoppingCartJpaRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Invalid cart id.")); // TODO: better exception
        List<ShoppingCartItemGetDTO> items = shoppingCartOrm.getItems().stream().map(this::createShoppingCartItemDto).collect(Collectors.toList());
        return new ShoppingCartGetDTO(shoppingCartOrm.getId(), items);
    }


    public ShoppingCartItemGetDTO getItemFromShoppingCart(Long cartId, Long itemId) {
        ShoppingCartItemOrm itemOrm = shoppingCartItemJpaRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Invalid item id.")); // TODO: better exception
        return createShoppingCartItemDto(itemOrm);
    }


    private ShoppingCartItemGetDTO createShoppingCartItemDto(ShoppingCartItemOrm itemOrm) {
        Long offerId = itemOrm.getOfferId();
        OfferExternalApi offer = offerApiClient // TODO: move this to separate method
                .getOffer(offerId, OfferExternalApi.class)
                .blockOptional()
                .orElseThrow(() -> new RuntimeException("")); // TODO: better exception

        TierExternalApi tier = offer.getTiers().stream()
                .filter(tierDto -> tierDto.getId().equals(itemOrm.getTierId()))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Could not extract tier data.")); // TODO: fix

        return new ShoppingCartItemGetDTO(
                itemOrm.getId(),
                itemOrm.getDescription(),
                offer.getId(),
                offer.getOwnerId(),
                offer.getTitle(),
                tier.getId(),
                tier.getTitle(),
                tier.getPrice()
        );
    }


    public Long addItemToCart(Long cartId, ShoppingCartItemPostDTO item) {
        OfferExternalApi offer = offerApiClient // TODO: move this to separate method
                .getOffer(item.getOfferId(), OfferExternalApi.class)
                .blockOptional()
                .orElseThrow(() -> new RuntimeException("")); // TODO: better exception

        Long desiredTierId = item.getTierId(); // TODO: move validation to separate method
        List<Long> possibleTierIds = offer.getTiers().stream().map(TierExternalApi::getId).collect(Collectors.toList());
        if(!possibleTierIds.contains(desiredTierId)) {
            throw new RuntimeException("Tier does not exist for given offer."); // TODO: better exception
        }

        ShoppingCartOrm shoppingCartOrm = shoppingCartJpaRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Invalid cart id.")); // TODO: better exception
        ShoppingCartItemOrm itemOrm = new ShoppingCartItemOrm();
        itemOrm.setOfferId(item.getOfferId());
        itemOrm.setTierId(item.getTierId());
        itemOrm.setDescription(item.getDescription());
        itemOrm.setCart(shoppingCartOrm);
        shoppingCartOrm.getItems().add(itemOrm); // TODO: handle better
        itemOrm = shoppingCartItemJpaRepository.saveAndFlush(itemOrm); // TODO: handle better
        return itemOrm.getId();
    }


    public Long editItemInCart(Long cartId, Long itemId, ShoppingCartItemPostDTO itemUpdate) { // TODO: add checking if item is in this cart, add cart checking
        ShoppingCartItemOrm itemOrm = shoppingCartItemJpaRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Invalid item id.")); // TODO: better exception
        itemOrm.setOfferId(itemUpdate.getOfferId());
        itemOrm.setDescription(itemUpdate.getDescription());
        itemOrm.setTierId(itemOrm.getTierId());
        return itemId;
    }


    public Long removeItemFromCart(Long cartId, Long itemId) { // TODO: add checking if item is in this cart
        if(!shoppingCartJpaRepository.existsById(cartId)) {
            throw new RuntimeException("Invalid cart id.");
        }
        shoppingCartItemJpaRepository.deleteById(itemId);
        return itemId;
    }
}
