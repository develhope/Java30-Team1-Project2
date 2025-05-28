package com.example.progetto_ecommerce_java30.auth.controller;

import com.example.progetto_ecommerce_java30.auth.dtoLoginRegister.LoginRequest;
import com.example.progetto_ecommerce_java30.auth.dtoLoginRegister.RegisterRequest;
import com.example.progetto_ecommerce_java30.auth.jwt.AuthService; // Usiamo UserServiceJwt
import com.example.progetto_ecommerce_java30.auth.dtoLoginRegister.LoginResponse; // Nuovo DTO per la risposta di login
import com.stripe.exception.StripeException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Endpoint per la registrazione di un nuovo utente.
     * Riceve un oggetto RegisterRequest nel corpo della richiesta HTTP.
     * @param request L'oggetto RegisterRequest contenente email e password.
     * @return ResponseEntity con un messaggio di successo o di errore.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            authService.register(request);
            return ResponseEntity.ok("User registered successfully.");
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Stripe error: " + e.getMessage());
        } catch (RuntimeException e) {
            // Gestione specifica per l'email già in uso
            if (e.getMessage().equals("Email already in use.")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed: " + e.getMessage());
        }
    }

    /**
     * Endpoint per il login dell'utente.
     * Riceve un oggetto LoginRequest nel corpo della richiesta HTTP.
     * @param request L'oggetto LoginRequest contenente email e password.
     * @return ResponseEntity con il JWT in caso di successo o un messaggio di errore.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            String token = authService.login(request); // UserServiceJwt.login ora restituisce il token
            return ResponseEntity.ok(new LoginResponse(token)); // Restituisci il token in un oggetto di risposta
        } catch (RuntimeException e) {
            // Include User not found o Wrong password
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    /**
     * Endpoint di esempio per recuperare i dettagli dell'utente attualmente autenticato.
     * Questo endpoint è protetto da Spring Security e richiede un JWT valido nell'header Authorization.
     * @param request La richiesta HTTP, usata per recuperare l'header Authorization.
     * @return ResponseEntity con l'email dell'utente corrente o un messaggio di errore.
     */
    // Questo endpoint sarà accessibile solo agli utenti autenticati via JWT
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        // Il filtro JWT avrà già autenticato l'utente e impostato il contesto di sicurezza
        // Qui potresti recuperare l'utente dal contesto di sicurezza o usare il metodo di UserServiceJwt
        return authService.getCurrentUser(request)
                .map(user -> ResponseEntity.ok("Current user email: " + user.getEmail()))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No authenticated user found."));
    }
}