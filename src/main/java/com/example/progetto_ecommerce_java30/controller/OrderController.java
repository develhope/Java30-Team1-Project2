package com.example.progetto_ecommerce_java30.controller;

import com.example.progetto_ecommerce_java30.entity.OrderEntity;
import com.example.progetto_ecommerce_java30.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping(path = "/select-all")
    public List<OrderEntity> allOrder(){
        return orderService.allOrder();
    }

    @PostMapping(path = "/create")
    public ResponseEntity<OrderEntity> addOrder(@RequestBody OrderEntity newOrder){
        return ResponseEntity.ok(orderService.addOrder(newOrder));
    }

    @GetMapping("/select-by-id/{id}")
    public ResponseEntity<OrderEntity> getOrderbyId(@PathVariable Long id){
        Optional<OrderEntity> foundOrder = orderService.getOrderById(id);

        return foundOrder
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<Void> deleteShoppingCartById(@PathVariable Long id){
        orderService.deleteOrderById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update-by-id/{id}")
    public ResponseEntity<OrderEntity> updateOrder(@PathVariable Long id, @RequestBody OrderEntity order) {
        if (id < 0) {
            return ResponseEntity.badRequest().build();
        }

        Optional<OrderEntity> orderUpdate = orderService.updateOrder(id, order);
        if (orderUpdate.isPresent()) {
            return ResponseEntity.ok(orderUpdate.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/close-cart-by-id/{orderId}")
    public ResponseEntity<OrderEntity> closeCartAfterOrder(@PathVariable Long orderId) {
        Optional<OrderEntity> optionalOrder = orderService.payOrder(orderId);

        return optionalOrder
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
