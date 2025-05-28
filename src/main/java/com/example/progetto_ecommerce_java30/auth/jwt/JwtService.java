package com.example.progetto_ecommerce_java30.auth.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value; // Import per @Value
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    // Carica la chiave segreta per la firma del JWT dal file application.properties
    // o dalle variabili d'ambiente. È fondamentale che questa chiave sia lunga e sicura.
    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    /**
     * Genera un nuovo JWT per un dato indirizzo email.
     * Il token include l'email come "subject", la data di emissione e la data di scadenza.
     * @param email L'indirizzo email dell'utente per cui generare il token.
     * @return Il JWT generato come stringa.
     */
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Data di emissione
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 1 giorno di validità
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Estrae l'indirizzo email (subject) da un JWT.
     * Se il token non è valido o scaduto, questa operazione lancerà un'eccezione.
     * @param token Il JWT da cui estrarre l'email.
     * @return L'indirizzo email estratto dal token.
     */
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Recupera la chiave di firma convertendola da stringa a oggetto Key.
     * @return L'oggetto Key utilizzato per firmare e validare i JWT.
     */
    private Key getSigningKey() {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
