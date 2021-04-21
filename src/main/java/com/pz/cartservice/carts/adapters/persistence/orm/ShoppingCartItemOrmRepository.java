package com.pz.cartservice.carts.adapters.persistence.orm;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartItemOrmRepository extends JpaRepository<ShoppingCartItemOrm, Long> {
}
