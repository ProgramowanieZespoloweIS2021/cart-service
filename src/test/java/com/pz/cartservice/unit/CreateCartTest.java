package com.pz.cartservice.unit;

import com.pz.cartservice.carts.domain.ShoppingCartService;
import com.pz.cartservice.carts.domain.repository.OfferRepository;
import com.pz.cartservice.carts.domain.repository.OrderRepository;
import com.pz.cartservice.carts.domain.repository.PaymentRepository;
import com.pz.cartservice.carts.domain.repository.ShoppingCartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateCartTest {

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
    public void validEmptyCartIsCreated() {
        Mockito.when(shoppingCartRepository.createCart()).thenReturn(1L);

        Long cartId = underTest.createEmptyShoppingCart();

        assertEquals(cartId, 1L);
        Mockito.verify(shoppingCartRepository, Mockito.times(1)).createCart();
    }
}
