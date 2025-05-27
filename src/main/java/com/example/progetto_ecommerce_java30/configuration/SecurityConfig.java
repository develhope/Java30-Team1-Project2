
package com.example.progetto_ecommerce_java30.configuration;

import com.example.progetto_ecommerce_java30.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for simplicity, consider enabling in production
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                // Allow public access to login and logout endpoints
                .requestMatchers("/auth/login", "/auth/logout").permitAll()
                // Permit GET requests to product endpoints (public viewing)
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/products/**").permitAll()
                // Require ADMIN role for product management (POST, PUT, DELETE)
                .requestMatchers(org.springframework.http.HttpMethod.POST, "/products/**").hasRole("ADMIN")
                .requestMatchers(org.springframework.http.HttpMethod.PUT, "/products/**").hasRole("ADMIN")
                .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/products/**").hasRole("ADMIN")
                // Require authentication for creating reviews (POST)
                .requestMatchers(org.springframework.http.HttpMethod.POST, "/reviews").authenticated()
                // Permit all other requests to review endpoints (public viewing)
                .requestMatchers("/reviews/**").permitAll()
                .anyRequest().authenticated() // Require authentication for all other requests by default
            )
            .logout(logout -> logout
                .logoutUrl("/logout") // Specify the logout URL
                .invalidateHttpSession(true) // Invalidate the HTTP session
                .deleteCookies("JSESSIONID") // Delete session cookie
            );

        return http.build();
    }

    // The AuthenticationManager is automatically configured by Spring Boot when a DaoAuthenticationProvider is provided.
    // You typically don't need to expose it as a separate bean unless you have specific advanced needs.
    // If you do need it, keep the previous bean definition.
    
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}