package com.example.progetto_ecommerce_java30.PaymentSystem.dto;

public class ConfirmRequest {
    private String intentId;
    private String paymentMethodId;

    public String getIntentId() { return intentId; }
    public void setIntentId(String intentId) { this.intentId = intentId; }

    public String getPaymentMethodId() { return paymentMethodId; }
    public void setPaymentMethodId(String paymentMethodId) { this.paymentMethodId = paymentMethodId; }
}