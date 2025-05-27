package com.example.progetto_ecommerce_java30.repository;

import com.example.progetto_ecommerce_java30.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
}