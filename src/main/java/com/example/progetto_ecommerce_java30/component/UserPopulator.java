package com.example.progetto_ecommerce_java30.component;

import com.example.progetto_ecommerce_java30.entity.UserEntity;
import com.example.progetto_ecommerce_java30.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class UserPopulator {

    @Autowired
    private UserService userService;

    public void populate() {
        List<UserEntity> users = new ArrayList<>();

        String[] nomi = {"Mario", "Luigi", "Giulia", "Francesca", "Carlo", "Anna", "Marco", "Sara", "Giorgio", "Luca", "Gino", "Vincent", "Fabiana", "Ciro", "Assunta", "Elvira"};
        String[] cognomi = {"Rozzi", "Bianchi", "Verdi", "Neri", "Ferrari", "Esposito", "Russo", "Conti", "Marino", "Ricci", "Santonicola", "Cupero", "Nguruge"};

        Random random = new Random();

        for (int i = 0; i < 1000; i++) {
            String name = nomi[random.nextInt(nomi.length)];
            String surname = cognomi[random.nextInt(cognomi.length)];

            int randomNumber = random.nextInt(1000);
            String email = name.toLowerCase() + surname.toLowerCase() + randomNumber + "@example.com";

            LocalDate birthDate = LocalDate.of(
                    random.nextInt(30) + 1980,  // Genera anno tra 1980 e 2010
                    random.nextInt(12) + 1,     // Genera mese tra 1 e 12
                    random.nextInt(28) + 1      // Genera giorno tra 1 e 28
            );
            LocalDate registrationDate = LocalDate.now().minusDays(random.nextInt(1000));

            boolean isActive = random.nextBoolean();

            UserEntity user = new UserEntity(
                    name,
                    surname,
                    email,
                    birthDate,
                    registrationDate,
                    isActive,
                    new ArrayList<>() // Ordini vuoti per ora
            );

            userService.addUser(user);
        }
    }
}

