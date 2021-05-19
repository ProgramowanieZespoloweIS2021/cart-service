package com.pz.cartservice.unit;

import com.pz.cartservice.carts.domain.ShoppingCartService;
import com.pz.cartservice.carts.domain.dto.ShoppingCartDetails;
import com.pz.cartservice.carts.domain.entity.ShoppingCart;
import com.pz.cartservice.carts.domain.repository.OfferRepository;
import com.pz.cartservice.carts.domain.repository.OrderRepository;
import com.pz.cartservice.carts.domain.repository.PaymentRepository;
import com.pz.cartservice.carts.domain.repository.ShoppingCartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GetExistingCartTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PaymentRepository paymentRepository;

    private ShoppingCartService underTest;

    @BeforeEach
    public void setUp() {
        shoppingCartRepository = Mockito.mock(ShoppingCartRepository.class);
        offerRepository = Mockito.mock(OfferRepository.class);
        orderRepository = Mockito.mock(OrderRepository.class);
        paymentRepository = Mockito.mock(PaymentRepository.class);
        underTest = new ShoppingCartService(shoppingCartRepository, offerRepository, orderRepository, paymentRepository);
    }

    @Test
    public void validEmptyCartIsReturned() { // TODO: add non-empty test
        Long existingCartId = 1L;
        ShoppingCart emptyShoppingCart = new ShoppingCart(existingCartId, Collections.emptyList());
        Mockito.when(shoppingCartRepository.get(existingCartId)).thenReturn(Optional.of(emptyShoppingCart));

        ShoppingCartDetails returnedCart = underTest.getExistingShoppingCart(existingCartId);

        assertEquals(returnedCart.getId(), emptyShoppingCart.getId());
        assertEquals(returnedCart.getItems().size(), emptyShoppingCart.getItems().size());
        Mockito.verify(shoppingCartRepository, Mockito.times(1)).get(existingCartId);
    }

    @Test
    public void accessingNonExistingCartFails() {
        Long nonExistingCartId = 1L;
        Mockito.when(shoppingCartRepository.get(nonExistingCartId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> underTest.getExistingShoppingCart(nonExistingCartId));
        Mockito.verify(shoppingCartRepository, Mockito.times(1)).get(nonExistingCartId);
    }
}
