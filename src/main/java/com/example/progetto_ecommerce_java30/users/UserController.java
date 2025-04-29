package com.example.progetto_ecommerce_java30.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserEntity> allUsers(){
        return userService.allUsers();
    }

    @PostMapping
    public ResponseEntity<UserEntity> addUser(@RequestBody UserEntity newUser){
        return ResponseEntity.ok(userService.addUser(newUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> userById(@PathVariable Long id){
        Optional<UserEntity> foundUser = userService.getUserById(id);

        return foundUser
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);

        return ResponseEntity.noContent().build();
    }

}
