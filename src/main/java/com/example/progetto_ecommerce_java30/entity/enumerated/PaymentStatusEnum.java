package com.example.progetto_ecommerce_java30.entity.enumerated;

public enum PaymentStatusEnum {
    PENDING,     // appena creato il PaymentIntent
    SUCCEEDED,   // pagamento andato a buon fine
    FAILED,      // rifiutato o scaduto
    CANCELED     // annullato manualmente o timeout
}
