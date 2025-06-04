package com.example.progetto_ecommerce_java30.auth;

import com.example.progetto_ecommerce_java30.auth.jwt.JwtAuthFilter; // Importa il filtro JWT
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // Import per il filtro

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter; // Inietta il filtro JWT

    /**
     * Definisce la catena di filtri di sicurezza che Spring Security userà.
     * Questo è il cuore della configurazione di sicurezza.
     * @param http L'oggetto HttpSecurity per configurare la sicurezza.
     * @return La SecurityFilterChain configurata.
     * @throws Exception In caso di errori di configurazione.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disabilita CSRF per API stateless
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // Permetti tutto ciò che c'è dopo auth
                        .requestMatchers("/user/**", "/product/**", "/shopping-cart/**", "/order/**", "/api/payments/**").permitAll()
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .anyRequest().authenticated() // Tutte le altre richieste richiedono autenticazione
                )
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Non usare sessioni HTTP
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Aggiungi il filtro JWT

        return http.build();
    }

    /**
     * Definisce e fornisce un bean PasswordEncoder.
     * Il PasswordEncoder viene utilizzato per codificare (hashing) le password prima di salvarle nel database
     * e per confrontare le password fornite durante il login con quelle codificate.
     * @return Un'istanza di BCryptPasswordEncoder, un algoritmo di hashing robusto.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}