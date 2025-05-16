package com.example.progetto_ecommerce_java30.component;

import com.example.progetto_ecommerce_java30.entity.ProductEntity;
import com.example.progetto_ecommerce_java30.entity.enumerated.ProductCategory;
import com.example.progetto_ecommerce_java30.entity.enumerated.ProductCondition;
import com.example.progetto_ecommerce_java30.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class ProductPopulator {

    @Autowired
    private ProductService productService;

    public void populate() {
        Random random = new Random();

        // Mappa contenente per ciascuna categoria dei nomi tipici per i prodotti.
        Map<ProductCategory, String[]> productNames = new HashMap<>();
        productNames.put(ProductCategory.ELECTRONICS, new String[]{"Smartphone", "Laptop", "Tablet", "Televisore", "Auricolari"});
        productNames.put(ProductCategory.GAMES, new String[]{"Videogioco", "Console", "PC Gaming", "Joystick", "Accessori per gaming"});
        productNames.put(ProductCategory.CLOTHINGS, new String[]{"T-shirt", "Jeans", "Giacca", "Scarpe", "Cappello"});
        productNames.put(ProductCategory.APPLIANCES, new String[]{"Frigorifero", "Microonde", "Lavatastiere", "Forno", "Aspirapolvere"});
        productNames.put(ProductCategory.BOOKS, new String[]{"Romanzo", "Saggio", "Manuale", "Biografia", "Libro per bambini"});

        for (int i = 0; i < 1000; i++) {
            // Selezione casuale della categoria
            ProductCategory category = ProductCategory.values()[random.nextInt(ProductCategory.values().length)];

            // Selezione del nome partendo dall'array specifico per la categoria scelta
            String[] namesForCategory = productNames.get(category);
            String name = namesForCategory[random.nextInt(namesForCategory.length)];

            // Creazione della descrizione in base al nome del prodotto
            String description = "Descrizione di " + name;

            // Selezione casuale della condizione del prodotto
            ProductCondition condition = ProductCondition.values()[random.nextInt(ProductCondition.values().length)];

            // Generazione di un prezzo compreso tra 10 e 1000, con due decimali
            BigDecimal price = BigDecimal.valueOf(10 + random.nextDouble() * 990)
                    .setScale(2, RoundingMode.HALF_UP);

            // Generazione di una data d'inserimento casuale negli ultimi 1000 giorni
            LocalDate insertDate = LocalDate.now().minusDays(random.nextInt(1000));

            // Stato attivo impostato casualmente
            boolean isActive = random.nextBoolean();

            // Creazione del ProductEntity
            ProductEntity product = new ProductEntity(name, description, category, condition, price, insertDate, isActive);

            // Persistenza del prodotto; presupponiamo che il metodo addProduct di ProductService si occupi del salvataggio
            productService.addProduct(product);
        }
    }
}
