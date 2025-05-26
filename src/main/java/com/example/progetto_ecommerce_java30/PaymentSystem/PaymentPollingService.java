package com.example.progetto_ecommerce_java30.PaymentSystem;

import com.example.progetto_ecommerce_java30.entity.OrderEntity;
import com.example.progetto_ecommerce_java30.entity.enumerated.PaymentStatusEnum;
import com.example.progetto_ecommerce_java30.repository.OrderRepository;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentPollingService {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private PaymentService paymentService;

    private static final Logger log = LoggerFactory.getLogger(PaymentPollingService.class);

    /**
     * Ogni minuto (configurabile) interroga Stripe per tutti gli ordini ancora PENDING.
     */
    @Scheduled(fixedDelayString = "${stripe.polling.delay:60000}")
    public void pollPendingPayments() {
        List<OrderEntity> pendingOrders = orderRepo.findByPaymentStatus(PaymentStatusEnum.PENDING);
        for (OrderEntity order : pendingOrders) {
            try {
                PaymentStatusEnum newStatus = paymentService.retrieveStatus(order.getPaymentIntentId());
                if (newStatus != order.getPaymentStatus()) {
                    order.setPaymentStatus(newStatus);
                    if (newStatus == PaymentStatusEnum.SUCCEEDED) {
                        order.setPaymentDate(LocalDate.now());
                        // eventualmente aggiorna anche orderShipping
                    }
                    orderRepo.save(order);
                }
            } catch (StripeException e) {
                // logga l’errore e continua: verrà riprovato al prossimo ciclo
                log.error("Errore polling per intent {}: {}",
                        order.getPaymentIntentId(), e.getMessage());
            }
        }
    }
}