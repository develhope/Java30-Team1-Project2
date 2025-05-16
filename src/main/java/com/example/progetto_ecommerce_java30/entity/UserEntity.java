package com.example.progetto_ecommerce_java30.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;

    @Email
    private String email;

    private LocalDate birthDate;
    private LocalDate registrationDate;

    private boolean isActive = true;

    @OneToOne
    @JoinColumn(name = "shopping_cart_id")
    private ShoppingCartEntity shopping_cart;

    @OneToMany
    @JoinColumn(name = "shopping_order_id")
    private List<OrderEntity> orders;

    private UserEntity() {
    }

    public UserEntity(String name, String surname, String email, LocalDate birthDate, LocalDate registrationDate, boolean isActive, List<OrderEntity> orders) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.birthDate = birthDate;
        this.registrationDate = registrationDate;
        this.isActive = isActive;
        this.orders = orders;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }

    public void addOrders(OrderEntity order) {
        this.orders.add(order);
    }

    public void removeOrders(OrderEntity order) {
        this.orders.remove(order);
    }
}
