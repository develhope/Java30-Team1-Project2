package com.example.progetto_ecommerce_java30.entity;

import com.example.progetto_ecommerce_java30.entity.enumerated.ShoppingCartStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "shopping_cart")
public class ShoppingCartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameCart;
    private BigDecimal finalPrice = BigDecimal.valueOf(0.0);
    private LocalDate creationDate;
    @Enumerated
    private ShoppingCartStatus shoppingCartStatus = ShoppingCartStatus.OPENED;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="shopping_cart_products",
            joinColumns = @JoinColumn(name="shopping_cart_id"),
            inverseJoinColumns = @JoinColumn(name="product_id"))
    private List<ProductEntity> products;

    private ShoppingCartEntity() {}

    public ShoppingCartEntity(Long id, String nameCart, LocalDate creationDate,
                              List<ProductEntity> products, ShoppingCartStatus shoppingCartStatus) {
        this.id = id;
        this.nameCart = nameCart;
        this.creationDate = creationDate;
        this.products = products;
        this.shoppingCartStatus = shoppingCartStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameCart() {
        return nameCart;
    }

    public void setNameCart(String nameCart) {
        this.nameCart = nameCart;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }


    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }

    public void addProduct(ProductEntity addProd){
        this.products.add(addProd);

        this.finalPrice =finalPrice.add(addProd.getPrice());
    }

    public void removeProduct(ProductEntity removeProd){
        this.products.remove(removeProd);

        this.finalPrice = finalPrice.subtract(removeProd.getPrice());
    }

    public ShoppingCartStatus getShoppingCartStatus() {
        return shoppingCartStatus;
    }

    public void setShoppingCartStatus(ShoppingCartStatus shoppingCartStatus) {
        this.shoppingCartStatus = shoppingCartStatus;
    }
}
