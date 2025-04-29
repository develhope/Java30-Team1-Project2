package com.example.progetto_ecommerce_java30.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> allUsers(){
        return userRepository.findAll();
    }

    public UserEntity addUser(UserEntity newUser){
        return userRepository.save(newUser);
    }

    public Optional<UserEntity> getUserById(Long id){
         return userRepository.findById(id);
    }

    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }

}
