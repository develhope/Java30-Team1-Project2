package com.example.progetto_ecommerce_java30.PaymentSystem;

import com.example.progetto_ecommerce_java30.entity.enumerated.PaymentStatusEnum;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {
    @Value("${stripe.apiKey}") private String apiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = apiKey;
    }

    public PaymentIntent createPayment(BigDecimal amount, String currency) throws StripeException, StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("amount", amount.multiply(new BigDecimal(100)).longValue());
        params.put("currency", currency);
        return PaymentIntent.create(params);
    }

    /**
     * Recupera lo stato corrente del PaymentIntent da Stripe.
     */
    public PaymentStatusEnum retrieveStatus(String intentId) throws StripeException {
        PaymentIntent intent = PaymentIntent.retrieve(intentId);
        String stripeStatus = intent.getStatus(); // es. "succeeded", "requires_payment_method", etc.
        // Mappa lo status Stripe sul tuo enum
        return switch (stripeStatus) {
            case "succeeded"         -> PaymentStatusEnum.SUCCEEDED;
            case "requires_payment_method" -> PaymentStatusEnum.FAILED;
            case "canceled"          -> PaymentStatusEnum.CANCELED;
            default                  -> PaymentStatusEnum.PENDING;
        };
    }

    /**
     * Conferma server-side il tuo PaymentIntent usando un paymentMethodId.
     */
    public PaymentIntent confirmPayment(String intentId, String paymentMethodId) throws StripeException {
        PaymentIntent intent = PaymentIntent.retrieve(intentId);
        Map<String,Object> params = new HashMap<>();
        params.put("payment_method", paymentMethodId); // es. "tok_visa"
        params.put("off_session", true);
        return intent.confirm(params);
    }
}