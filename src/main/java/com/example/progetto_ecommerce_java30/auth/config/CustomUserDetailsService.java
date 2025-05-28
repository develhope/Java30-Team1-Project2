package com.example.progetto_ecommerce_java30.auth.config; // O una tua cartella config

import com.example.progetto_ecommerce_java30.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Carica i dettagli dell'utente dato il suo username (nel nostro caso, l'email).
     * Questa è un'interfaccia chiave di Spring Security utilizzata per recuperare le informazioni
     * necessarie per costruire un oggetto UserDetails, che Spring Security usa per l'autenticazione.
     * @param email L'email (username) dell'utente da caricare.
     * @return Un oggetto UserDetails che rappresenta l'utente autenticato.
     * @throws UsernameNotFoundException Se nessun utente è trovato con l'email fornita.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(user -> org.springframework.security.core.userdetails.User.builder()
                        .username(user.getEmail())
                        .password(user.getPassword()) // La password è già codificata
                        .roles("USER") // Assegna un ruolo di default
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
}