package com.example.progetto_ecommerce_java30.PaymentSystem;

import com.example.progetto_ecommerce_java30.PaymentSystem.dto.ConfirmRequest;
import com.example.progetto_ecommerce_java30.PaymentSystem.dto.PaymentRequest;
import com.example.progetto_ecommerce_java30.entity.OrderEntity;
import com.example.progetto_ecommerce_java30.entity.enumerated.PaymentStatusEnum;
import com.example.progetto_ecommerce_java30.repository.OrderRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments/")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderRepository orderRepository;

//    @PostMapping
//    public ResponseEntity<Map<String,Object>> pay(@RequestBody PaymentRequest req) throws StripeException {
//        PaymentIntent pi = paymentService.createPayment(req.getAmount(), req.getCurrency());
//        return ResponseEntity.ok(Map.of(
//                "clientSecret", pi.getClientSecret()
//        ));
//    }

    // 1) crei il PaymentIntent
    @PostMapping("/create")
    public ResponseEntity<Map<String,Object>> create(@RequestBody PaymentRequest req) throws StripeException {
        // 1) crea il PaymentIntent
        PaymentIntent pi = paymentService.createPayment(req.getAmount(), req.getCurrency());

        // 2) salva l'ordine in DB
        OrderEntity order = new OrderEntity();
        order.setOrderNumber(/* genera qui il numero, es. sequenziale */);
        order.setPaymentIntentId(pi.getId());
        order.setPaymentStatus(PaymentStatusEnum.PENDING);
        orderRepository.save(order);

        // 3) ritorna clientSecret, intentId e orderId
        return ResponseEntity.ok(Map.of(
                "clientSecret", pi.getClientSecret(),
                "intentId",     pi.getId(),
                "orderId",      order.getId().toString()
        ));
    }

    // 2) confermi il PaymentIntent usando un test token
    @PostMapping("/confirm")
    public ResponseEntity<Map<String,String>> confirm(@RequestBody ConfirmRequest req) throws StripeException {
        PaymentIntent pi = paymentService.confirmPayment(req.getIntentId(), req.getPaymentMethodId());
        return ResponseEntity.ok(Map.of("status", pi.getStatus()));
    }
}
