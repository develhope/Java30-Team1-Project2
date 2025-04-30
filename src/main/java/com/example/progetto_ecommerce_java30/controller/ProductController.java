package com.example.progetto_ecommerce_java30.controller;

import com.example.progetto_ecommerce_java30.entity.ProductEntity;
import com.example.progetto_ecommerce_java30.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductEntity> allProducts(){
        return  productService.getAllProducts();
    }

    @PostMapping
    public ResponseEntity<ProductEntity> addProduct(@RequestBody ProductEntity newProduct){
        return ResponseEntity.ok(productService.addProduct(newProduct));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> productById(@PathVariable Long id){
        Optional<ProductEntity> foundProduct = productService.productById(id);

        return foundProduct
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id){
        productService.deleteProductById(id);

        return ResponseEntity.noContent().build();
    }
}
