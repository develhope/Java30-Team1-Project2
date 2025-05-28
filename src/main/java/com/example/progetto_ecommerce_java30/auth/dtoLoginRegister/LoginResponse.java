package com.example.progetto_ecommerce_java30.auth.dtoLoginRegister;

public class LoginResponse {
    // Campo per il JSON Web Token (JWT) generato dopo un login di successo.
    // Questo token verrà utilizzato dal client per le richieste autenticate successive.
    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}