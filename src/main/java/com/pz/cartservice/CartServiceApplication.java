package com.pz.cartservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.MalformedURLException;
import java.net.URL;

@SpringBootApplication
public class CartServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartServiceApplication.class, args);
    }

    // TODO: move to config???
    @Bean(name = "offersUrl")
    public URL offersUrl() throws MalformedURLException {
        return new URL("http://localhost:8080/offers");
    }

}