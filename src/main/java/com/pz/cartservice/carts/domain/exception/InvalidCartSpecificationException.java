package com.pz.cartservice.carts.domain.exception;

public class InvalidCartSpecificationException extends RuntimeException {

    public InvalidCartSpecificationException(String message) {
        super(message);
    }
}
