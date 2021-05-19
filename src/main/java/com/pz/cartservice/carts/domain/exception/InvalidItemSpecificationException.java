package com.pz.cartservice.carts.domain.exception;

public class InvalidItemSpecificationException extends RuntimeException {

    public InvalidItemSpecificationException(String message) {
        super(message);
    }
}
