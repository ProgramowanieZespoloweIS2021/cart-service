package com.pz.cartservice.carts.externalapi;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URL;

@Component
public class OfferApiClient {

    private final WebClient webClient;

    public OfferApiClient(URL offersUrl) {
        this.webClient = WebClient.builder().baseUrl(offersUrl.toString()).build();
    }

    public <T> Mono<T> getOffer(Long offerId, Class<T> aClass) {
        return webClient.get().uri("/" + offerId).retrieve().bodyToMono(aClass);
    }

}
