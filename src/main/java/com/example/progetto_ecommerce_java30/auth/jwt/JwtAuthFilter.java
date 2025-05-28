package com.example.progetto_ecommerce_java30.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService; // UserDetailsService per caricare i dettagli dell'utente
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService; // UserDetailsService per caricare i dettagli dell'utente

    /**
     * Metodo principale del filtro. Viene chiamato per ogni richiesta HTTP.
     * Controlla l'header "Authorization" per un token JWT.
     * Se un token valido è trovato, autentica l'utente nel contesto di sicurezza di Spring.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        try {
            userEmail = jwtService.extractEmail(jwt);
        } catch (Exception e) {
            // Gestione token non valido o scaduto
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or expired JWT token.");
            return;
        }


        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Carica i dettagli dell'utente (potresti dover implementare una UserDetailsService)
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // Valida il token e imposta l'autenticazione nel contesto di sicurezza
            // Nota: JwtService non ha un metodo `validateToken`. Si assume che `extractEmail`
            // fallisca se il token non è valido o scaduto. Se vuoi una validazione esplicita,
            // dovresti aggiungerla a JwtService e chiamarla qui.
            // Per esempio: if (jwtService.validateToken(jwt, userDetails)) { ... }
            if (userDetails != null) { // Una validazione più robusta sarebbe desiderabile qui
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}