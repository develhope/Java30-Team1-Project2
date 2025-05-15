package com.example.progetto_ecommerce_java30.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
// Carrello: id, nome, prezzoFinale(da valuatare), dataCreazione(da valutare).

@Entity(name = "shopping_cart")
public class ShoppingCartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameCart;
    private Integer finalPrice;
    private LocalDate creationDate;

    @OneToOne(mappedBy = "shopping_cart")
    private UserEntity user;

    @ManyToMany(
            fetch   = FetchType.LAZY,
            cascade = { CascadeType.PERSIST, CascadeType.MERGE }
    )
    @JoinTable(name="shopping_cart_products",
            joinColumns = @JoinColumn(name="shopping_cart_id"),
            inverseJoinColumns = @JoinColumn(name="product_id")
    )
    private List<ProductEntity> products;

    @OneToOne
    private OrderEntity shopping_order;

    private ShoppingCartEntity() {}

    public ShoppingCartEntity(Long id, String nameCart, Integer finalPrice, LocalDate creationDate, List<ProductEntity> products,
                              OrderEntity shopping_order) {
        this.id = id;
        this.nameCart = nameCart;
        this.finalPrice = finalPrice;
        this.creationDate = creationDate;
        this.products = products;
        this.shopping_order = shopping_order;
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

    public Integer getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Integer finalPrice) {
        this.finalPrice = finalPrice;
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

    public OrderEntity getShopping_order() {
        return shopping_order;
    }

    public void setShopping_order(OrderEntity shopping_order) {
        this.shopping_order = shopping_order;
    }
}
