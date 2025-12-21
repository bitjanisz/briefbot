package com.admeliora.briefbot.application.order.port.out;

import com.admeliora.briefbot.application.order.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderPort {
    Order save(Order order);
    Optional<Order> findById(Long id);
    List<Order> findAll();
    boolean existsById(Long id);
    Order getReferenceById(Long id);
    void delete(Order order);
}

