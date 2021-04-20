package com.pz.cartservice.carts.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;

@Configuration
public class ShoppingCartConfig {

    @Bean
    public URL offersUrl(@Value("${offers.url}") String offersUrl) throws MalformedURLException {
        return new URL(offersUrl);
    }

    @Bean
    public URL ordersUrl(@Value("${orders.url}") String ordersUrl) throws MalformedURLException {
        return new URL(ordersUrl);
    }
}
