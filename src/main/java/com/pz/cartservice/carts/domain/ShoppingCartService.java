package com.pz.cartservice.carts.domain;

import com.pz.cartservice.carts.adapters.api.ShoppingCartItemRequest;
import com.pz.cartservice.carts.domain.entity.*;
import com.pz.cartservice.carts.domain.repository.OfferRepository;
import com.pz.cartservice.carts.domain.repository.OrderRepository;
import com.pz.cartservice.carts.domain.repository.PaymentRepository;
import com.pz.cartservice.carts.domain.repository.ShoppingCartRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final OfferRepository offerRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;


    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository,
                               OfferRepository offerRepository,
                               OrderRepository orderRepository,
                               PaymentRepository paymentRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.offerRepository = offerRepository;
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }


    public void submitCart(Long cartId, Long buyerId) { // TODO: add validation for user
        if(!cartExists(cartId)) {
            throw new RuntimeException("Invalid cart id."); // TODO: better exception and message
        }
        ShoppingCart shoppingCart = shoppingCartRepository.getCart(cartId).orElseThrow(() -> new RuntimeException("Invalid cart id."));
        shoppingCart.getItems().forEach(this::insertOfferDetailsIntoShoppingCartItem);
        BigDecimal totalPrice = shoppingCart.getItems().stream()
                .map(item -> item.getTier().getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Payment payment = new Payment(buyerId, totalPrice, "IN_PROGRESS");
        List<Order> orders = shoppingCart.getItems().stream()
                .map(item -> new Order(buyerId, item.getOffer().getId(), item.getTier().getId(), item.getDescription()))
                .collect(Collectors.toList());
        orderRepository.add(orders);
        paymentRepository.add(payment);
        shoppingCartRepository.deleteCart(cartId);
    }


    public Long createEmptyShoppingCart() {
        return shoppingCartRepository.createCart();
    }


    public ShoppingCart getExistingShoppingCart(Long cartId) {
        ShoppingCart shoppingCart = shoppingCartRepository.getCart(cartId).orElseThrow(() -> new RuntimeException("Invalid cart id.")); // TODO: better exception
        shoppingCart.getItems().forEach(this::insertOfferDetailsIntoShoppingCartItem);
        return shoppingCart;
    }


    public Long addItemToCart(Long cartId, ShoppingCartItemRequest item) {
        if(!offerAndTierExist(item.getOfferId(), item.getTierId())) {
            throw new RuntimeException("Invalid cart id."); // TODO: better exception and message
        }
        return shoppingCartRepository.addItemToCart(cartId, item);
    }


    public Long removeItemFromCart(Long cartId, Long itemId) {
        if(!cartContainsItem(cartId, itemId)) {
            throw new RuntimeException("Invalid cart id."); // TODO: better exception and message
        }
        shoppingCartRepository.deleteItem(itemId);
        return itemId;
    }


    public ShoppingCartItem getItemFromShoppingCart(Long cartId, Long itemId) {
        if(!cartContainsItem(cartId, itemId)) {
            throw new RuntimeException("Invalid cart id."); // TODO: better exception and message
        }
        ShoppingCartItem shoppingCartItem = shoppingCartRepository.getItem(itemId).orElseThrow(() -> new RuntimeException("Invalid item id.")); // TODO: better exception
        insertOfferDetailsIntoShoppingCartItem(shoppingCartItem);
        return shoppingCartItem;
    }


    public Long editItemInCart(Long cartId, Long itemId, ShoppingCartItemRequest itemUpdate) {
        if(!cartContainsItem(cartId, itemId)) {
            throw new RuntimeException("Invalid cart id."); // TODO: better exception and message
        }
        shoppingCartRepository.editItem(itemId, itemUpdate);
        return itemId;
    }


    private Boolean cartExists(Long cartId) {
        return shoppingCartRepository.getCart(cartId).isPresent();
    }


    private Boolean cartContainsItem(Long cartId, Long itemId) {
        ShoppingCart shoppingCart = shoppingCartRepository.getCart(cartId).orElseThrow(() -> new RuntimeException("Invalid cart id.")); // TODO: better exception
        return shoppingCart.getItems().stream().anyMatch(item -> item.getId().equals(itemId));
    }


    private Boolean offerAndTierExist(Long offerId, Long tierId) {
        Optional<Offer> offer = offerRepository.get(offerId);
        if(offer.isEmpty()) {
            return false;
        }
        return offer.get().getTiers().stream().anyMatch(tier -> tier.getId().equals(tierId));
    }


    private void insertOfferDetailsIntoShoppingCartItem(ShoppingCartItem shoppingCartItem) {
        Offer offer = offerRepository.get(shoppingCartItem.getOffer().getId())
                .orElseThrow(() -> new RuntimeException("Invalid cart id.")); // TODO: better exception
        shoppingCartItem.getOffer().setOwnerId(offer.getOwnerId());
        shoppingCartItem.getOffer().setTitle(offer.getTitle());
        shoppingCartItem.getOffer().setTiers(offer.getTiers());

        Long selectedTierId = shoppingCartItem.getTier().getId();
        Tier selectedTier = offer.getTiers().stream()
                .filter(tier -> tier.getId().equals(selectedTierId)).findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid cart id.")); // TODO: better exception
        shoppingCartItem.getTier().setTitle(selectedTier.getTitle());
        shoppingCartItem.getTier().setPrice(selectedTier.getPrice());
    }

}
