package com.pz.cartservice.carts.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartItemJpaRepository extends JpaRepository<ShoppingCartItemOrm, Long> {
}