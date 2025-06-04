package com.example.progetto_ecommerce_java30.controller;

import com.example.progetto_ecommerce_java30.component.ProductPopulator;
import com.example.progetto_ecommerce_java30.entity.ProductEntity;
import com.example.progetto_ecommerce_java30.entity.enumerated.ProductCategory;
import com.example.progetto_ecommerce_java30.entity.enumerated.ProductCondition;
import com.example.progetto_ecommerce_java30.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductPopulator productPopulator;

    @GetMapping(path = "/select-all")
    public List<ProductEntity> allProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/add-product")
    public ResponseEntity<ProductEntity> addProduct(@RequestBody ProductEntity newProduct) {
        return ResponseEntity.ok(productService.addProduct(newProduct));
    }

    @GetMapping("/select-by-id/{id}")
    public ResponseEntity<ProductEntity> productById(@PathVariable Long id) {
        Optional<ProductEntity> foundProduct = productService.productById(id);

        return foundProduct
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("/delete-by-id/{id}")
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

    @PostMapping("/example/5-products")
    public ResponseEntity<ProductEntity> aggiungi5prodotti() {
        productService.addProduct(new ProductEntity("Tastiera Asus", "serve per scrivere", ProductCategory.ELECTRONICS,
                ProductCondition.NEW, BigDecimal.valueOf(15.99), LocalDate.now(), true));

        productService.addProduct(new ProductEntity("Tastiera Logictech", "serve per scrivere", ProductCategory.ELECTRONICS,
                ProductCondition.NEW, BigDecimal.valueOf(39.99), LocalDate.now(), true));

        productService.addProduct(new ProductEntity("Tastiera Mac", "serve per scrivere con stile", ProductCategory.ELECTRONICS,
                ProductCondition.NEW, BigDecimal.valueOf(159.99), LocalDate.now(), true));

        productService.addProduct(new ProductEntity("Tastiera Microsoft", "e' bella che altro serve a fa", ProductCategory.ELECTRONICS,
                ProductCondition.NEW, BigDecimal.valueOf(19.99), LocalDate.now(), true));

        productService.addProduct(new ProductEntity("Tastiera Ginaus Ergonomics", "serve per scrivere in modo ergonomico", ProductCategory.ELECTRONICS,
                ProductCondition.NEW, BigDecimal.valueOf(75.99), LocalDate.now(), true));

        return ResponseEntity.accepted().build();
    }



@PutMapping("/update-by-id/{id}")
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
