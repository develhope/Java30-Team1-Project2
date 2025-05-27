package com.example.progetto_ecommerce_java30.PaymentSystem;

import com.example.progetto_ecommerce_java30.PaymentSystem.dto.ConfirmRequest;
import com.example.progetto_ecommerce_java30.PaymentSystem.dto.PaymentRequest;
import com.example.progetto_ecommerce_java30.entity.OrderEntity;
import com.example.progetto_ecommerce_java30.entity.enumerated.PaymentStatusEnum;
import com.example.progetto_ecommerce_java30.repository.OrderRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/payments/")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderRepository orderRepository;
    // 1) crei il PaymentIntent
    @PostMapping("/create")
    public ResponseEntity<Map<String,Object>> create(@RequestBody PaymentRequest req) throws StripeException {
        PaymentIntent pi = paymentService.createPayment(req.getAmount(), req.getCurrency());
        return ResponseEntity.ok(Map.of(
                "intentId",     pi.getId(),
                "clientSecret", pi.getClientSecret()
        ));
    }

    @PostMapping("/confirm")
    public ResponseEntity<Map<String,String>> confirm(@RequestBody ConfirmRequest req) throws StripeException {
        // 1) conferma su Stripe
        PaymentIntent pi = paymentService.confirmPayment(req.getIntentId(), req.getPaymentMethodId());

        // 2) recupera l'OrderEntity esistente
        OrderEntity order = orderRepository.findById(req.getOrderId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ordine non trovato"));

        // 3) aggiorna lo stato e la data
        boolean succeeded = "succeeded".equals(pi.getStatus());
        order.setPaymentStatus(succeeded
                ? PaymentStatusEnum.SUCCEEDED
                : PaymentStatusEnum.FAILED);
        if (succeeded) {
            order.setPaymentDate(LocalDate.now());
        }
        orderRepository.save(order);

        // 4) ritorna lo status
        return ResponseEntity.ok(Map.of("status", pi.getStatus()));
    }
}
