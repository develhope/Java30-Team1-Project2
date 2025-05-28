package com.example.progetto_ecommerce_java30.auth.dtoLoginRegister;

public class LoginRequest {
    // Campo per l'email dell'utente. Usato per identificare l'utente durante il login.
    public String email;
    // Campo per la password dell'utente. Sarà confrontata con la password codificata nel DB.
    public String password;
}