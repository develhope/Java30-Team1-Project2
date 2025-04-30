package com.example.progetto_ecommerce_java30.repository;

import com.example.progetto_ecommerce_java30.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
