package com.example.progetto_ecommerce_java30.controller;

import com.example.progetto_ecommerce_java30.component.UserPopulator;
import com.example.progetto_ecommerce_java30.entity.UserEntity;
import com.example.progetto_ecommerce_java30.service.UserService;
import com.stripe.exception.StripeException; // Importa StripeException
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

    @GetMapping(path = "/select-all")
    public List<UserEntity> allUsers() {
        return userService.allUsers();
    }

    @PostMapping("/create")
    public ResponseEntity<?> addUser(@RequestBody UserEntity newUser) { // Cambiato il tipo di ritorno a ResponseEntity<?>
        try {
            UserEntity savedUser = userService.addUser(newUser);
            return ResponseEntity.ok(savedUser);
        } catch (RuntimeException e) { // Cattura l'eccezione per email già in uso
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (StripeException e) { // Cattura l'eccezione di Stripe
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Stripe error: " + e.getMessage());
        }
    }

    @GetMapping("/select-by-id/{id}")
    public ResponseEntity<UserEntity> userById(@PathVariable Long id) {
        Optional<UserEntity> foundUser = userService.getUserById(id);

        return foundUser
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PutMapping("/update-by-id/{id}")
    public ResponseEntity<UserEntity> updateUserById(@PathVariable Long id, @RequestBody UserEntity userToUpdate){
        Optional<UserEntity> userUpdated = userService.updateById(id, userToUpdate);

        return userUpdated.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete-by-id/{id}")
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
