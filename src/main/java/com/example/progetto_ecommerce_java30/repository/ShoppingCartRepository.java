package com.example.progetto_ecommerce_java30.repository;

import com.example.progetto_ecommerce_java30.entity.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Long> {
}
