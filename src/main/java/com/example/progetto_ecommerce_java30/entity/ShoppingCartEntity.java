package com.example.progetto_ecommerce_java30.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
// Carrello: id, nome, prezzoFinale(da valuatare), dataCreazione(da valutare).

@Entity(name = "shopping_cart")
public class ShoppingCartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameCart;
    private Integer finalPrice;
    private LocalDate creationDate;

    private ShoppingCartEntity() {}

    public ShoppingCartEntity(Long id, String nameCart, Integer finalPrice, LocalDate creationDate) {
        this.id = id;
        this.nameCart = nameCart;
        this.finalPrice = finalPrice;
        this.creationDate = creationDate;
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
}
