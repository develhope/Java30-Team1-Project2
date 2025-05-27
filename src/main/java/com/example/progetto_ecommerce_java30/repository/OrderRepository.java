package com.example.progetto_ecommerce_java30.repository;

import com.example.progetto_ecommerce_java30.entity.OrderEntity;
import com.example.progetto_ecommerce_java30.entity.enumerated.PaymentStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findByPaymentStatus(PaymentStatusEnum status);

}
