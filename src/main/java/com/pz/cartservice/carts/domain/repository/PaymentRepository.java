package com.pz.cartservice.carts.domain.repository;

import com.pz.cartservice.carts.domain.entity.Payment;

public interface PaymentRepository {

    void add(Payment payment);
}
