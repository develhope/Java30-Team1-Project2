package com.example.progetto_ecommerce_java30.service;

import com.example.progetto_ecommerce_java30.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
}
