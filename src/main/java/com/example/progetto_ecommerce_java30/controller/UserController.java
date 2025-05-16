package com.example.progetto_ecommerce_java30.controller;

import com.example.progetto_ecommerce_java30.component.UserPopulator;
import com.example.progetto_ecommerce_java30.entity.UserEntity;
import com.example.progetto_ecommerce_java30.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserPopulator userPopulator;

    @GetMapping
    public List<UserEntity> allUsers() {
        return userService.allUsers();
    }

    @PostMapping
    public ResponseEntity<UserEntity> addUser(@RequestBody UserEntity newUser) {
        return ResponseEntity.ok(userService.addUser(newUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> userById(@PathVariable Long id) {
        Optional<UserEntity> foundUser = userService.getUserById(id);

        return foundUser
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    //wildcard generica di Java, Il punto interrogativo rappresenta un tipo generico sconosciuto e accetta più tipi di oggetti.
    //la utilizzo per dare un body alla ResponseEntity (badRequest)
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        Optional<UserEntity> userToDeactivate = userService.deleteUserById(id);

        if (userToDeactivate.isPresent()) {
            return ResponseEntity.ok(userToDeactivate.get());
        }

        return ResponseEntity.badRequest().body("L'id inserito non è valido.");

    }

    @PostMapping("/sample")
    @ResponseStatus(HttpStatus.OK)
    public void addSampleUsers() {
        userPopulator.populate();
    }

}
