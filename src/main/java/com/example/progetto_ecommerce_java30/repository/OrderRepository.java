package com.example.progetto_ecommerce_java30.repository;

import com.example.progetto_ecommerce_java30.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
