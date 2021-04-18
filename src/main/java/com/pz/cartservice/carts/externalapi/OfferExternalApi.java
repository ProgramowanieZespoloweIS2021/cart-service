package com.pz.cartservice.carts.externalapi;

import java.util.List;

public class OfferExternalApi {

    private Long id;
    private Long ownerId;
    private String title;
    private List<TierExternalApi> tiers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TierExternalApi> getTiers() {
        return tiers;
    }

    public void setTiers(List<TierExternalApi> tiers) {
        this.tiers = tiers;
    }
}
