package com.pz.cartservice.carts.domain.repository;

import com.pz.cartservice.carts.domain.entity.Offer;

import java.util.Optional;


public interface OfferRepository {

    Optional<Offer> get(Long offerId);

}
