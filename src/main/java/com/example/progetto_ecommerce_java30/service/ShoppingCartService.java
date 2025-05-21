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

    public Optional<ShoppingCartEntity> updateCartById(Long id, ShoppingCartEntity cartToUpdate){
        if(shoppingCartRepository.existsById(id)){
            cartToUpdate.setId(id);
            return Optional.of(shoppingCartRepository.save(cartToUpdate));
        }
        return Optional.empty();
    }

    public void deleteShoppingCartById(Long id){
        shoppingCartRepository.deleteById(id);
    }

    public Optional<ShoppingCartEntity> addProduct(Long cartID, ProductEntity product) {
        Optional<ShoppingCartEntity> cartEntity = shoppingCartRepository.findById(cartID);

        if (cartEntity.isPresent()) {
            cartEntity.get().addProduct(product);
            ShoppingCartEntity savedEntity = shoppingCartRepository.save(cartEntity.get());
            return Optional.of(savedEntity);
        }
         return Optional.empty();
    }

}
