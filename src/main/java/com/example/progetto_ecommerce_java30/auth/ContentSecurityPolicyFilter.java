package com.example.progetto_ecommerce_java30.auth;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ContentSecurityPolicyFilter implements Filter {
    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        HttpServletResponse resp = (HttpServletResponse) response;

        // Impostiamo la Content-Security-Policy desiderata:
        String csp = "default-src 'self'; " +
                "script-src 'self' 'unsafe-inline' https://js.stripe.com https://m.stripe.network; " +
                "style-src 'self' 'unsafe-inline' https://m.stripe.network; " +
                "img-src 'self' https://m.stripe.network https://files.stripe.com data:; " +
                "connect-src 'self' https://api.stripe.com; " +
                "font-src 'self' https://m.stripe.network;";

        resp.setHeader("Content-Security-Policy", csp);
        // Se vuoi anche una policy in sola “report‐only”:
        // resp.setHeader("Content-Security-Policy-Report-Only", csp);

        chain.doFilter(request, response);
    }
}
