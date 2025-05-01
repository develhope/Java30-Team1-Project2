package com.example.progetto_ecommerce_java30.service;

import com.example.progetto_ecommerce_java30.entity.UserEntity;
import com.example.progetto_ecommerce_java30.repository.UserRepository;
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

    public Optional<UserEntity> deleteUserById(Long id){
        if(userRepository.existsById(id)){
            UserEntity userToDeactivate = userRepository.findById(id).get();

            userToDeactivate.setActive(false);

            return Optional.of(userRepository.save(userToDeactivate));
        }

        return Optional.empty();
    }

}
