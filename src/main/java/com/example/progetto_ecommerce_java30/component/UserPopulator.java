package com.example.progetto_ecommerce_java30.component;

import com.example.progetto_ecommerce_java30.entity.UserEntity;
import com.example.progetto_ecommerce_java30.service.UserService;
import com.stripe.exception.StripeException; // Importa StripeException
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger; // Importa Logger
import org.slf4j.LoggerFactory; // Importa LoggerFactory

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class UserPopulator {

    private static final Logger logger = LoggerFactory.getLogger(UserPopulator.class); // Inizializza il logger

    @Autowired
    private UserService userService;

    public void populate() {
        logger.info("Starting user population...");
        List<UserEntity> users = new ArrayList<>(); // Non è strettamente necessario mantenere questa lista se non la usi dopo

        String[] nomi = {"Mario", "Luigi", "Giulia", "Francesca", "Carlo", "Anna", "Marco", "Sara", "Giorgio", "Luca", "Gino", "Vincent", "Fabiana", "Ciro", "Assunta", "Elvira"};
        String[] cognomi = {"Rozzi", "Bianchi", "Verdi", "Neri", "Ferrari", "Esposito", "Russo", "Conti", "Marino", "Ricci", "Santonicola", "Cupero", "Nguruge"};

        Random random = new Random();

        for (int i = 0; i < 100; i++) { // Ridotto a 100 utenti per un test più rapido
            String name = nomi[random.nextInt(nomi.length)];
            String surname = cognomi[random.nextInt(cognomi.length)];

            int randomNumber = random.nextInt(100000); // Aumentato il range per minor probabilità di duplicati
            String email = name.toLowerCase() + surname.toLowerCase() + randomNumber + "@example.com";

            LocalDate birthDate = LocalDate.of(
                    random.nextInt(30) + 1980,  // Genera anno tra 1980 e 2010
                    random.nextInt(12) + 1,     // Genera mese tra 1 e 12
                    random.nextInt(28) + 1      // Genera giorno tra 1 e 28
            );
            LocalDate registrationDate = LocalDate.now().minusDays(random.nextInt(1000));

            boolean isActive = random.nextBoolean();

            String password = "password" + random.nextInt(100); // Password leggermente più variegate per test

            UserEntity user = new UserEntity(
                    name,
                    surname,
                    email,
                    password, // Questa password sarà codificata dal UserService
                    birthDate,
                    registrationDate,
                    isActive,
                    new ArrayList<>() // Ordini vuoti per ora
            );

            try {
                userService.addUser(user); // addUser ora gestisce hashing e Stripe
                // users.add(user); // Se vuoi mantenere un riferimento agli utenti creati
                logger.debug("User created: {}", user.getEmail());
            } catch (RuntimeException e) {
                logger.warn("Failed to create user {}: {}", email, e.getMessage());
            } catch (StripeException e) {
                logger.error("Stripe error when creating user {}: {}", email, e.getMessage());
            }
        }
        logger.info("User population finished.");
    }
}