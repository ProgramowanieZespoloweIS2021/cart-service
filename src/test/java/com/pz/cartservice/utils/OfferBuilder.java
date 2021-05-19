package com.pz.cartservice.utils;

import com.pz.cartservice.carts.domain.entity.Offer;
import com.pz.cartservice.carts.domain.entity.Tier;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OfferBuilder {

    private Long id;
    private Long ownerId;
    private String title;
    private List<Tier> tiers = new ArrayList<>();

    public OfferBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public OfferBuilder withOwner(Long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public OfferBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public OfferBuilder withTiers(List<Tier> tiers) {
        this.tiers = tiers;
        return this;
    }

    public OfferBuilder withTier(Long tierId, String tierTitle, BigDecimal tierPrice) {
        Tier tier = new Tier();
        tier.setId(tierId);
        tier.setTitle(tierTitle);
        tier.setPrice(tierPrice);
        this.tiers.add(tier);
        return this;
    }

    public Offer build() {
        Offer offer = new Offer();
        offer.setId(id);
        offer.setOwnerId(ownerId);
        offer.setTitle(title);
        offer.setTiers(tiers);
        return offer;
    }

}
