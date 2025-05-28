package com.example.progetto_ecommerce_java30.service;

import com.example.progetto_ecommerce_java30.entity.UserEntity;
import com.example.progetto_ecommerce_java30.repository.UserRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
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
        return userRepository.findById(id);
    }

    public Optional<UserEntity> updateById(Long id, UserEntity userToUpdate){
        if(userRepository.existsById(id)){
            // Recupera l'utente esistente per non sovrascrivere campi importanti come password/stripeCustomerId
            //TODO fare il check di questa findById (isPresent)
            UserEntity existingUser = userRepository.findById(id).get();

            // Aggiorna solo i campi consentiti (es. name, surname, email se necessario)
            existingUser.setName(userToUpdate.getName());
            existingUser.setSurname(userToUpdate.getSurname());
            existingUser.setEmail(userToUpdate.getEmail());
            // TODO: Se la password deve essere aggiornata, dovrebbe esserci un endpoint separato con ricodifica
            // existingUser.setPassword(passwordEncoder.encode(userToUpdate.getPassword()));
            existingUser.setBirthDate(userToUpdate.getBirthDate());
            existingUser.setActive(userToUpdate.isActive());

            return Optional.of(userRepository.save(existingUser));
        }
        return Optional.empty();
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
