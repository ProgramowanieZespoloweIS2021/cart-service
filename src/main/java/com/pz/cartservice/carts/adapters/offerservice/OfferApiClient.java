package com.pz.cartservice.carts.adapters.offerservice;

import com.pz.cartservice.carts.domain.repository.OfferRepository;
import com.pz.cartservice.carts.domain.entity.Offer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URL;
import java.util.Optional;


@Component
public class OfferApiClient implements OfferRepository {

    private final WebClient webClient;

    public OfferApiClient(URL offersUrl) {
        this.webClient = WebClient.builder().baseUrl(offersUrl.toString()).build();
    }

    @Override
    public Optional<Offer> get(Long offerId) {
        return webClient.get().uri("/offers/" + offerId).retrieve().bodyToMono(Offer.class).blockOptional();
    }

}
