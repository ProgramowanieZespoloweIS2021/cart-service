package com.pz.cartservice.carts.adapters.orderservice;

import com.pz.cartservice.carts.domain.entity.Order;
import com.pz.cartservice.carts.domain.repository.OrderRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URL;
import java.util.List;


@Component
public class OrderApiClient implements OrderRepository {

    private final WebClient webClient;

    public OrderApiClient(URL ordersUrl) {
        this.webClient = WebClient.builder().baseUrl(ordersUrl.toString()).build();
    }

    @Override
    public void add(List<Order> orders) {
        webClient.post().uri("/orders").body(Mono.just(orders), List.class).retrieve().toBodilessEntity().block();
    }
}
