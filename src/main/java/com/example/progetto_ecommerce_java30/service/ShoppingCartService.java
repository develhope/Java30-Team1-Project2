package com.example.progetto_ecommerce_java30.service;

import com.example.progetto_ecommerce_java30.entity.ProductEntity;
import com.example.progetto_ecommerce_java30.entity.ShoppingCartEntity;
import com.example.progetto_ecommerce_java30.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public List<ShoppingCartEntity> getAllShoppingCart(){
        return shoppingCartRepository.findAll();
    }

    public ShoppingCartEntity addShoppingCart(ShoppingCartEntity newShoppingCart){
        return shoppingCartRepository.save(newShoppingCart);
    }

    public Optional<ShoppingCartEntity> shoppingCartById(Long id){
        return shoppingCartRepository.findById(id);
    }

    public void deleteShoppingCartById(Long id){
        shoppingCartRepository.deleteById(id);
    }
}
