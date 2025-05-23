package com.example.progetto_ecommerce_java30.controller;

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

    @DeleteMapping("/clear-cart/{id}")
    public ResponseEntity<ShoppingCartEntity> clearCart(@PathVariable Long id){
        Optional<ShoppingCartEntity> clearCart = shoppingCartService.clearCart(id);

        return clearCart.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/add-product/{cartID}")
    public ResponseEntity<ShoppingCartEntity> addProduct(@PathVariable Long cartID, @RequestParam Long productID){
        Optional<ShoppingCartEntity> addProductToCart = shoppingCartService.addProduct(cartID, productID);

        return addProductToCart.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/remove-product/{cartID}")
    public ResponseEntity<ShoppingCartEntity> removeProduct(@PathVariable Long cartID, @RequestParam Long productID){
        Optional<ShoppingCartEntity> removeProductFromCart = shoppingCartService.removeProduct(cartID, productID);

        if(removeProductFromCart.isPresent()){
            return ResponseEntity.ok(removeProductFromCart.get());
        }
        return ResponseEntity.badRequest().build();
    }

    @PatchMapping("/example/{cartID}")
    public ResponseEntity<ShoppingCartEntity> add5Product(@PathVariable Long cartID){
        Optional<ShoppingCartEntity> addProductToCart = shoppingCartService.add5Product(cartID, 4L);
        Optional<ShoppingCartEntity> addProductToCart1 = shoppingCartService.add5Product(cartID, 5L);
        Optional<ShoppingCartEntity> addProductToCart2 = shoppingCartService.add5Product(cartID, 8L);
        Optional<ShoppingCartEntity> addProductToCart3 = shoppingCartService.add5Product(cartID, 13L);
        Optional<ShoppingCartEntity> addProductToCart4 = shoppingCartService.add5Product(cartID, 35L);


        return addProductToCart.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
