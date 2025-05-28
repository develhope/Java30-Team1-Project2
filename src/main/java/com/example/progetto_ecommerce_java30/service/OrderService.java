package com.example.progetto_ecommerce_java30.service;

import com.example.progetto_ecommerce_java30.entity.OrderEntity;
import com.example.progetto_ecommerce_java30.entity.ShoppingCartEntity;
import com.example.progetto_ecommerce_java30.entity.enumerated.OrderShippingEnum;
import com.example.progetto_ecommerce_java30.entity.enumerated.ShoppingCartStatus;
import com.example.progetto_ecommerce_java30.repository.OrderRepository;
import com.example.progetto_ecommerce_java30.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public List<OrderEntity> allOrder(){
        return orderRepository.findAll();
    }

    public OrderEntity addOrder(OrderEntity newOrder){
        return orderRepository.save(newOrder);
    }

    public Optional<OrderEntity> getOrderById(Long id){
        return orderRepository.findById(id);
    }

    public void deleteOrderById(Long id){
        orderRepository.deleteById(id);
    }

    public Optional<OrderEntity> updateOrder(Long id, OrderEntity order){
        Optional<OrderEntity> orderFound = orderRepository.findById(id);

        if(orderFound.isPresent()){
            order.setId(id);
            return Optional.of(orderRepository.save(order));
        }
        return Optional.empty();
    }

    public Optional<OrderEntity> payOrder(Long orderId) {
        Optional<OrderEntity> orderOptional = orderRepository.findById(orderId);

       if (orderOptional.isPresent()) {

           OrderEntity order = orderOptional.get();
           ShoppingCartEntity shoppingCart = order.getShoppingCart();

           shoppingCart.setShoppingCartStatus(ShoppingCartStatus.CLOSED);
           order.setOrderShipping(OrderShippingEnum.PAYED);

           OrderEntity updateOrder = orderRepository.save(order);

           return Optional.of(updateOrder);

       }

       return Optional.empty();


    }

    /**
     * Nuovo metodo: mappa l'OrderEntity su una struttura
     * con i soli campi che ti interessano per lo status.
     */
    public Optional<Map<String,Object>> getOrderStatus(Long id) {
        return orderRepository.findById(id)
                .map(order -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("orderId",       order.getId());
                    result.put("orderNumber",   order.getOrderNumber());
                    result.put("paymentStatus", order.getPaymentStatus());
                    // puoi decidere di mettere una stringa vuota se è null, oppure tenerlo null
                    result.put("paymentDate",   order.getPaymentDate());
                    return result;
                });
    }
}
