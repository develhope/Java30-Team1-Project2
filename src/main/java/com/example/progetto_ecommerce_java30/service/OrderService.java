package com.example.progetto_ecommerce_java30.service;

import com.example.progetto_ecommerce_java30.entity.OrderEntity;
import com.example.progetto_ecommerce_java30.entity.ProductEntity;
import com.example.progetto_ecommerce_java30.entity.UserEntity;
import com.example.progetto_ecommerce_java30.repository.OrderRepository;
import com.example.progetto_ecommerce_java30.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    private UserRepository userRepository;

    public List<OrderEntity> allOrder(){
        return orderRepository.findAll();
    }

    public OrderEntity addOrder(OrderEntity newOrder){
        return orderRepository.save(newOrder);
    }

    public Optional<OrderEntity> getOrderById(Long id){
        return orderRepository.findById(id);
    }

    public void deleteOrderById(Long id){
        orderRepository.deleteById(id);
    }

    public Optional<OrderEntity> updateOrder(Long id, OrderEntity order){
        if(!orderRepository.existsById(id)){
            return Optional.empty();
        }

        order.setId(id);
        return Optional.of(orderRepository.save(order));
    }
}
