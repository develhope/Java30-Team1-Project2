package com.example.progetto_ecommerce_java30.controller;

import com.example.progetto_ecommerce_java30.component.ProductPopulator;
import com.example.progetto_ecommerce_java30.entity.ProductEntity;
import com.example.progetto_ecommerce_java30.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductPopulator productPopulator;

    @GetMapping
    public List<ProductEntity> allProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    public ResponseEntity<ProductEntity> addProduct(@RequestBody ProductEntity newProduct) {
        return ResponseEntity.ok(productService.addProduct(newProduct));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> productById(@PathVariable Long id) {
        Optional<ProductEntity> foundProduct = productService.productById(id);

        return foundProduct
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductEntity> deleteProductById(@PathVariable Long id) {
        Optional<ProductEntity> product = productService.deleteProductById(id);

        return product
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/populate")
    @ResponseStatus(HttpStatus.OK)
    public void addSampleUsers() {
        productPopulator.populate();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductEntity> updateProduct(@PathVariable Long id, @RequestBody ProductEntity product) {
        if (id < 0) {
            return ResponseEntity.badRequest().build();
        }

        Optional<ProductEntity> productUpdate = productService.updateProduct(id, product);
        if (productUpdate.isPresent()) {
            return ResponseEntity.ok(productUpdate.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
