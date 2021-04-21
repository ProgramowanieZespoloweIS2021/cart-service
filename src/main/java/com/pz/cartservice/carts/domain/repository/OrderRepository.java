package com.pz.cartservice.carts.domain.repository;

import com.pz.cartservice.carts.domain.entity.Order;

import java.util.List;


public interface OrderRepository {

    void add(List<Order> orders);

}
