package com.example.progetto_ecommerce_java30.service;

import com.example.progetto_ecommerce_java30.entity.ProductEntity;
import com.example.progetto_ecommerce_java30.entity.ShoppingCartEntity;
import com.example.progetto_ecommerce_java30.repository.ProductRepository;
import com.example.progetto_ecommerce_java30.repository.ShoppingCartRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ProductRepository productRepository;

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

    public Optional<ShoppingCartEntity> clearCart(Long id){
        Optional<ShoppingCartEntity> cart = shoppingCartRepository.findById(id);

        if (cart.isPresent()) {
            cart.get().getProducts().clear();
           return Optional.of(shoppingCartRepository.save(cart.get()));
        }

       return Optional.empty();
    }

    public Optional<ShoppingCartEntity> addProduct(Long cartID, Long productID) {
        Optional<ShoppingCartEntity> cartEntity = shoppingCartRepository.findById(cartID);
        Optional<ProductEntity> productEntity = productRepository.findById(productID);

        if (cartEntity.isPresent() && productEntity.isPresent()) {
            cartEntity.get().addProduct(productEntity.get());
            ShoppingCartEntity savedEntity = shoppingCartRepository.save(cartEntity.get());
            return Optional.of(savedEntity);
        }
         return Optional.empty();
    }

    public Optional<ShoppingCartEntity> add5Product(Long cartID, Long productID) {
        Optional<ShoppingCartEntity> cartEntity = shoppingCartRepository.findById(cartID);
        Optional<ProductEntity> productEntity = productRepository.findById(productID);

        if (cartEntity.isPresent() && productEntity.isPresent()) {
            cartEntity.get().addProduct(productEntity.get());
            ShoppingCartEntity savedEntity = shoppingCartRepository.save(cartEntity.get());
            return Optional.of(savedEntity);
        }
        return Optional.empty();
    }

    public Optional<ShoppingCartEntity> removeProduct(Long cartID, Long productID){
        Optional<ShoppingCartEntity> cartEntity = shoppingCartRepository.findById(cartID);
        Optional<ProductEntity> productEntity = productRepository.findById(productID);

        if(cartEntity.isPresent() && productEntity.isPresent()){
            cartEntity.get().removeProduct(productEntity.get());
            ShoppingCartEntity savedEntity = shoppingCartRepository.save(cartEntity.get());
            return Optional.of(savedEntity);
        }

        return Optional.empty();
    }
}
