package com.example.progetto_ecommerce_java30.entity;

import com.example.progetto_ecommerce_java30.entity.enumerated.ProductCategory;
import com.example.progetto_ecommerce_java30.entity.enumerated.ProductCondition;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    @Enumerated(EnumType.STRING)
    private ProductCondition productCondition;

    private BigDecimal price;
    private LocalDate insertDate;

    @ManyToMany(mappedBy = "products")
    private List<ShoppingCartEntity> shoppingCarts;

    private boolean isActive = true;

    private ProductEntity() {
    }

    public ProductEntity(String name, String description, ProductCategory category,
                         ProductCondition condition, BigDecimal price, LocalDate insertDate,
                         boolean isActive, List<ShoppingCartEntity> shoppingCarts) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.productCondition = condition;
        this.price = price;
        this.insertDate = insertDate;
        this.isActive = isActive;
        this.shoppingCarts = shoppingCarts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public ProductCondition getProductCondition() {
        return productCondition;
    }

    public void setProductCondition(ProductCondition productCondition) {
        this.productCondition = productCondition;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(LocalDate insertDate) {
        this.insertDate = insertDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<ShoppingCartEntity> getShoppingCarts() {
        return shoppingCarts;
    }

    public void setShoppingCarts(List<ShoppingCartEntity> shoppingCarts) {
        this.shoppingCarts = shoppingCarts;
    }

    public void addShoppingCarts(ShoppingCartEntity shoppingCart) {
        this.shoppingCarts.add(shoppingCart);
    }

    public void deleteShoppingCarts(ShoppingCartEntity shoppingCart) {
        this.shoppingCarts.remove(shoppingCart);
    }
}
