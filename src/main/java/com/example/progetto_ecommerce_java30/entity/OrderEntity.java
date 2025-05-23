package com.example.progetto_ecommerce_java30.entity;

import com.example.progetto_ecommerce_java30.entity.enumerated.OrderShippingEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity(name = "shopping_order")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderNumber;
    private String address;

    @Enumerated(EnumType.STRING)
    private OrderShippingEnum orderShipping;
    private LocalDate paymentDate;

    @OneToOne
    private ShoppingCartEntity shoppingCart;

    private OrderEntity() {}

    public OrderEntity(Long id, Long orderNumber, String address, OrderShippingEnum orderShipping, LocalDate paymentDate,
                       ShoppingCartEntity shoppingCart) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.address = address;
        this.orderShipping = orderShipping;
        this.paymentDate = paymentDate;
        this.shoppingCart = shoppingCart;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public OrderShippingEnum getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(OrderShippingEnum orderShipping) {
        this.orderShipping = orderShipping;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public ShoppingCartEntity getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCartEntity shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

}
