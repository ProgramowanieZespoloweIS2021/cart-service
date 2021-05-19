package com.pz.cartservice.utils;

import com.pz.cartservice.carts.domain.entity.Offer;

import java.math.BigDecimal;
import java.util.List;

public class SampleOffersProvider {

    public List<Offer> get() {
        Offer firstOffer = getFirstOffer();
        Offer secondOffer = getSecondOffer();
        Offer thirdOffer = getThirdOffer();
        return List.of(firstOffer, secondOffer, thirdOffer);
    }

    private Offer getFirstOffer() {
        return new OfferBuilder()
                .withId(1L).withOwner(1L).withTitle("Sample offer 1")
                .withTier(1L, "Tier 1", new BigDecimal("2.00"))
                .withTier(2L, "Tier 2", new BigDecimal("5.50"))
                .withTier(3L, "Tier 3", new BigDecimal("2.20"))
                .build();
    }

    private Offer getSecondOffer() {
        return new OfferBuilder()
                .withId(2L).withOwner(4L).withTitle("Sample offer 2")
                .withTier(4L, "Tier 4", new BigDecimal("100.00"))
                .build();
    }

    private Offer getThirdOffer() {
        return new OfferBuilder()
                .withId(3L).withOwner(1L).withTitle("Sample offer 3")
                .withTier(5L, "Tier 5", new BigDecimal("50.00"))
                .withTier(6L, "Tier 6", new BigDecimal("10.00"))
                .build();

    }

}
