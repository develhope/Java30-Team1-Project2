package com.example.progetto_ecommerce_java30.service;

import com.example.progetto_ecommerce_java30.entity.ProductEntity;
import com.example.progetto_ecommerce_java30.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    public ProductEntity addProduct(ProductEntity newProduct) {
        return productRepository.save(newProduct);
    }

    public Optional<ProductEntity> productById(Long id) {
        return productRepository.findById(id);
    }

    public Optional<ProductEntity> deleteProductById(Long id) {
        if (productRepository.existsById(id)) {
            ProductEntity product = productRepository.findById(id).get();
            product.setActive(false);

            return Optional.of(productRepository.save(product));
        }

        return Optional.empty();
    }

    public Optional<ProductEntity> updateProduct(Long id, ProductEntity product){
        if(!productRepository.existsById(id)){
            return Optional.empty();
        }

        product.setId(id);
        return Optional.of(productRepository.save(product));
    }
}
