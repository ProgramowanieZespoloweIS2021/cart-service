package com.pz.cartservice.carts.adapters.paymentservice;

import com.pz.cartservice.carts.domain.entity.Payment;
import com.pz.cartservice.carts.domain.repository.PaymentRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URL;

@Component
public class PaymentApiClient implements PaymentRepository {

    private final WebClient webClient;

    public PaymentApiClient(URL paymentUrl) {
        this.webClient = WebClient.builder().baseUrl(paymentUrl.toString()).build();
    }

    @Override
    public void add(Payment payment) {
        webClient.post().uri("/payments").body(Mono.just(payment), Payment.class).retrieve().toBodilessEntity().block();
    }
}
