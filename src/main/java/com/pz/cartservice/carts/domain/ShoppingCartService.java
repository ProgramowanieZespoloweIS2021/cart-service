package com.pz.cartservice.carts.domain;

import com.pz.cartservice.carts.domain.dto.ShoppingCartDetails;
import com.pz.cartservice.carts.domain.dto.ShoppingCartItemDetails;
import com.pz.cartservice.carts.domain.entity.*;
import com.pz.cartservice.carts.domain.exception.InvalidCartSpecificationException;
import com.pz.cartservice.carts.domain.exception.InvalidItemSpecificationException;
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


    public void submitCart(Long cartId, Long buyerId) {
        ShoppingCart shoppingCart = shoppingCartRepository.get(cartId)
                .orElseThrow(() -> new InvalidCartSpecificationException(String.format("Cart with ID %d does not exist.", cartId)));
        if(shoppingCart.getItems().isEmpty()) {
            throw new InvalidCartSpecificationException(String.format("Can not submit cart with ID %d because it is empty.", cartId));
        }
        Payment payment = getPaymentForGivenCart(shoppingCart, buyerId);
        List<Order> orders = getOrdersForGivenCart(shoppingCart, buyerId);
        orderRepository.add(orders);
        paymentRepository.add(payment);
        shoppingCartRepository.delete(cartId);
    }


    public Long createEmptyShoppingCart() {
        ShoppingCart shoppingCart = ShoppingCart.emptyCart();
        return shoppingCartRepository.add(shoppingCart);
    }


    public ShoppingCartDetails getExistingShoppingCart(Long cartId) {
        ShoppingCart shoppingCart = shoppingCartRepository.get(cartId)
                .orElseThrow(() -> new InvalidCartSpecificationException(String.format("Cart with ID %d does not exist.", cartId)));
        List<ShoppingCartItemDetails> itemDetails = shoppingCart.getItems().stream()
                .map(this::fetchOfferDetailsForGivenItem)
                .collect(Collectors.toList());
        BigDecimal totalPrice = getTotalPriceForGivenCart(shoppingCart);
        return new ShoppingCartDetails(shoppingCart.getId(), totalPrice, itemDetails);
    }


    public void addItemToCart(Long cartId, ShoppingCartItem shoppingCartItem) {
        if(!offerAndTierExist(shoppingCartItem.getOfferId(), shoppingCartItem.getTierId())) {
            throw new InvalidItemSpecificationException("Invalid offer or tier specification.");
        }
        ShoppingCart shoppingCart = shoppingCartRepository.get(cartId)
                .orElseThrow(() -> new InvalidCartSpecificationException(String.format("Cart with ID %d does not exist.", cartId)));
        shoppingCart.addItem(shoppingCartItem);
        shoppingCartRepository.add(shoppingCart);
    }


    public void removeItemFromCart(Long cartId, Long itemId) {
        ShoppingCart shoppingCart = shoppingCartRepository.get(cartId)
                .orElseThrow(() -> new InvalidCartSpecificationException(String.format("Cart with ID %d does not exist.", cartId)));
        shoppingCart.removeItem(itemId);
        shoppingCartRepository.add(shoppingCart);
    }


    public ShoppingCartItemDetails getItemFromShoppingCart(Long cartId, Long itemId) {
        ShoppingCart shoppingCart = shoppingCartRepository.get(cartId)
                .orElseThrow(() -> new InvalidCartSpecificationException(String.format("Cart with ID %d does not exist.", cartId)));
        return fetchOfferDetailsForGivenItem(shoppingCart.getItem(itemId));
    }


    public void editItemInCart(Long cartId, ShoppingCartItem shoppingCartItem) {
        ShoppingCart shoppingCart = shoppingCartRepository.get(cartId)
                .orElseThrow(() -> new InvalidCartSpecificationException(String.format("Cart with ID %d does not exist.", cartId)));
        shoppingCart.updateItem(shoppingCartItem);
        shoppingCartRepository.add(shoppingCart);
    }


    private BigDecimal getItemPrice(ShoppingCartItem shoppingCartItem) {
        Offer offer = offerRepository.get(shoppingCartItem.getOfferId())
                .orElseThrow(() -> new RuntimeException(String.format("Could not access offer with ID %d to compute total price.", shoppingCartItem.getOfferId())));
        Tier chosenTier = offer.getTiers().stream()
                .filter(tier -> tier.getId().equals(shoppingCartItem.getTierId())).findFirst()
                .orElseThrow(() -> new RuntimeException("Could not access tier to compute total price."));
        return chosenTier.getPrice();
    }


    private BigDecimal getTotalPriceForGivenCart(ShoppingCart shoppingCart) {
        return shoppingCart.getItems().stream()
                .map(this::getItemPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    private Payment getPaymentForGivenCart(ShoppingCart shoppingCart, Long buyerId) {
        BigDecimal totalPrice = getTotalPriceForGivenCart(shoppingCart);
        return new Payment(buyerId, totalPrice, "IN_PROGRESS");
    }


    private List<Order> getOrdersForGivenCart(ShoppingCart shoppingCart, Long buyerId) {
        return shoppingCart.getItems().stream()
                .map(item -> new Order(buyerId, item.getOfferId(), item.getTierId(), item.getDescription()))
                .collect(Collectors.toList());
    }


    private Boolean offerAndTierExist(Long offerId, Long tierId) {
        Optional<Offer> offer = offerRepository.get(offerId);
        if(offer.isEmpty()) {
            return false;
        }
        return offer.get().getTiers().stream().anyMatch(tier -> tier.getId().equals(tierId));
    }


    private ShoppingCartItemDetails fetchOfferDetailsForGivenItem(ShoppingCartItem shoppingCartItem) {
        Offer offer = offerRepository.get(shoppingCartItem.getOfferId())
                .orElseThrow(() -> new RuntimeException(String.format("Could not access offer with ID %d.", shoppingCartItem.getOfferId())));
        return ShoppingCartItemDetails.fromShoppingCartItem(shoppingCartItem, offer);
    }

}
