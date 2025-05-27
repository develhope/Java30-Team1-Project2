package com.example.progetto_ecommerce_java30.service;

import com.example.progetto_ecommerce_java30.entity.UserEntity;
import com.example.progetto_ecommerce_java30.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // For now, assuming no specific roles. You can add logic to fetch user roles later.
        return new User(
                userEntity.getEmail(),
                userEntity.getPassword(), // You need to handle password encoding/matching
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")) // Example role
        );
    }
}