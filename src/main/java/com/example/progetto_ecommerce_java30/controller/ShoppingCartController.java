package com.example.progetto_ecommerce_java30.controller;

import com.example.progetto_ecommerce_java30.entity.ProductEntity;
import com.example.progetto_ecommerce_java30.entity.ShoppingCartEntity;
import com.example.progetto_ecommerce_java30.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping
    public List<ShoppingCartEntity> allShoppingCart(){
        return  shoppingCartService.getAllShoppingCart();
    }

    @PostMapping
    public ResponseEntity<ShoppingCartEntity> addShoppingCart(@RequestBody ShoppingCartEntity newShoppingCart){
        return ResponseEntity.ok(shoppingCartService.addShoppingCart(newShoppingCart));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingCartEntity> shoppingCartById(@PathVariable Long id){
        Optional<ShoppingCartEntity> foundShoppingCart = shoppingCartService.shoppingCartById(id);

        return foundShoppingCart
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PutMapping("/{id}")
    public ResponseEntity<ShoppingCartEntity> updateCartById(@PathVariable Long id, @RequestBody ShoppingCartEntity cartToUpdate){
        Optional<ShoppingCartEntity> updatedCart = shoppingCartService.updateCartById(id, cartToUpdate);

        return updatedCart.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteShoppingCartById(@PathVariable Long id){
        shoppingCartService.deleteShoppingCartById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/add-product/{id}")
    public ResponseEntity<ShoppingCartEntity> addProduct(@PathVariable Long id, @RequestBody ProductEntity product){
        Optional<ShoppingCartEntity> addProductToCart = shoppingCartService.addProduct(id, product);

        return addProductToCart.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
