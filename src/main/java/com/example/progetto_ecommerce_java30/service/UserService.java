package com.example.progetto_ecommerce_java30.service;

import com.example.progetto_ecommerce_java30.entity.UserEntity;
import com.example.progetto_ecommerce_java30.repository.UserRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value; // Import per @Value
import org.springframework.security.crypto.password.PasswordEncoder; // Import per PasswordEncoder
import org.springframework.stereotype.Service;

import java.time.LocalDate; // Per la data di registrazione
import java.util.List;
import java.util.Map; // Per la creazione del cliente Stripe
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Inietta PasswordEncoder

    @Value("${stripe.api.key}") // Inietta la chiave Stripe
    private String stripeApiKey;

    public List<UserEntity> allUsers(){
        return userRepository.findAll();
    }

    // Metodo modificato per aggiungere un utente con password codificata e Stripe Customer
    public UserEntity addUser(UserEntity newUser) throws StripeException {
        // Verifica se l'email è già in uso
        if (userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use.");
        }

        // Codifica la password prima di salvarla
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        // Imposta la data di registrazione se non è già impostata
        if (newUser.getRegistrationDate() == null) {
            newUser.setRegistrationDate(LocalDate.now());
        }

        // Inizializza Stripe e crea il cliente
        Stripe.apiKey = stripeApiKey;
        Customer customer = Customer.create(Map.of("email", newUser.getEmail()));
        newUser.setStripeCustomerId(customer.getId()); // Salva l'ID del cliente Stripe

        return userRepository.save(newUser);
    }

    public Optional<UserEntity> getUserById(Long id){
        Optional<UserEntity> userFound = userRepository.findById(id);

        if(userFound.isPresent()){
            return userFound;
        }

        return Optional.empty();
    }

    public Optional<UserEntity> updateById(Long id, UserEntity userToUpdate){
        // Recupera l'utente esistente per non sovrascrivere campi importanti come password/stripeCustomerId
        Optional<UserEntity> userFound = userRepository.findById(id);

        if(userFound.isPresent()){
            // Aggiorna solo i campi consentiti (es. name, surname, email se necessario)
            userFound.get().setName(userToUpdate.getName());
            userFound.get().setSurname(userToUpdate.getSurname());
            userFound.get().setEmail(userToUpdate.getEmail());
            // TODO: Se la password deve essere aggiornata, dovrebbe esserci un endpoint separato con ricodifica
            // existingUser.setPassword(passwordEncoder.encode(userToUpdate.getPassword()));
            userFound.get().setBirthDate(userToUpdate.getBirthDate());
            userFound.get().setActive(userToUpdate.isActive());

            return Optional.of(userRepository.save(userFound.get()));
        }
        return Optional.empty();
    }

    public Optional<UserEntity> deleteUserById(Long id){
        Optional<UserEntity> userFound = userRepository.findById(id);

        if(userFound.isPresent()){
            userFound.get().setActive(false);

            return Optional.of(userRepository.save(userFound.get()));
        }

        return Optional.empty();
    }

}
