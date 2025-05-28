package com.example.progetto_ecommerce_java30.auth.jwt;

import com.example.progetto_ecommerce_java30.auth.dtoLoginRegister.LoginRequest;
import com.example.progetto_ecommerce_java30.auth.dtoLoginRegister.RegisterRequest;
import com.example.progetto_ecommerce_java30.entity.UserEntity;
import com.example.progetto_ecommerce_java30.repository.UserRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value; // Import per @Value
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    public static final String AUTHORIZATION = "Authorization";
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    @Value("${stripe.api.key}") // Carica la chiave Stripe da application.properties o variabili d'ambiente
    private String stripeApiKey;

    /**
     * Registra un nuovo utente nel sistema.
     * - Verifica se l'email è già in uso.
     * - Inizializza Stripe con la chiave API.
     * - Crea un nuovo cliente su Stripe e salva il suo ID.
     * - Codifica la password dell'utente.
     * - Salva l'utente nel database.
     * @param request L'oggetto RegisterRequest contenente email e password del nuovo utente.
     * @throws StripeException Se si verifica un errore durante l'interazione con Stripe.
     * @throws RuntimeException Se l'email è già in uso.
     */
    public void register(RegisterRequest request) throws StripeException {
        // Controlla se esiste già un utente con la stessa email
        if (userRepository.findByEmail(request.email).isPresent()) {
            throw new RuntimeException("Email already in use.");
        }

        // Imposta la chiave API di Stripe per la libreria Stripe
        Stripe.apiKey = stripeApiKey; // Inizializza Stripe con la chiave caricata
        Customer customer = Customer.create(Map.of("email", request.email));

        UserEntity user = new UserEntity();
        user.setEmail(request.email);
        user.setPassword(passwordEncoder.encode(request.password));
        user.setStripeCustomerId(customer.getId());
        userRepository.save(user);
    }

    /**
     * Gestisce il processo di login dell'utente.
     * - Trova l'utente per email.
     * - Verifica che la password fornita corrisponda alla password codificata nel DB.
     * - Se le credenziali sono valide, genera un JWT.
     * @param request L'oggetto LoginRequest contenente email e password per il login.
     * @return Il JWT generato in caso di successo.
     * @throws RuntimeException Se l'utente non è trovato o la password è errata.
     */
    public String login(LoginRequest request) { // Rimosso HttpSession
        UserEntity user = userRepository.findByEmail(request.email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(request.password, user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        // Se le credenziali sono valide, genera un JWT per l'utente
        String token = jwtService.generateToken(user.getEmail());
        return token;
    }

    /**
     * Recupera l'utente attualmente autenticato dalla richiesta HTTP.
     * - Estrae il JWT dall'header "Authorization".
     * - Estrae l'email dal JWT.
     * - Trova l'utente corrispondente nel database.
     * @param request La richiesta HTTP corrente.
     * @return Un Optional contenente l'UserEntity se un utente è autenticato e valido, altrimenti Optional.empty().
     */
    public Optional<UserEntity> getCurrentUser(HttpServletRequest request) {
        //TODO non possono esserci stringa, bisogna fare campi final messi in cima.
        String authHeader = request.getHeader(AUTHORIZATION);
        // Controlla se l'header di autorizzazione è presente e inizia con "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) return Optional.empty();
        // Estrae il token JWT rimuovendo il prefisso "Bearer "
        String token = authHeader.substring(7);
        try {
            // Estrae l'email dal token JWT. Se il token è invalido/scaduto, lancia un'eccezione.
            String email = jwtService.extractEmail(token);
            // Cerca l'utente nel database tramite l'email estratta
            return userRepository.findByEmail(email);
        } catch (Exception e) {
            // Gestire eccezioni come token scaduto o non valido
            return Optional.empty();
        }
    }
}