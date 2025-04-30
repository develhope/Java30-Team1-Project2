package com.example.progetto_ecommerce_java30.shoppingCart;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
}
