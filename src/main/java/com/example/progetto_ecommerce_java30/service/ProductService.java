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
        Optional<ProductEntity> productFound = productRepository.findById(id);

        if (productFound.isPresent()) {
            productFound.get().setActive(false);

            return Optional.of(productRepository.save(productFound.get()));
        }

        return Optional.empty();
    }

    public Optional<ProductEntity> updateProduct(Long id, ProductEntity product){
        Optional<ProductEntity> productFound = productRepository.findById(id);

        if(productFound.isPresent()){
            product.setId(id);
            return Optional.of(productRepository.save(product));
        }

        return Optional.empty();
    }
}
