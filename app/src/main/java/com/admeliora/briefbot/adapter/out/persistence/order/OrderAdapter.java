package com.admeliora.briefbot.adapter.out.persistence.order;

import com.admeliora.briefbot.adapter.out.persistence.order.jpa.OrderRepositoryJpa;
import com.admeliora.briefbot.application.order.model.Order;
import com.admeliora.briefbot.application.order.port.out.OrderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderAdapter implements OrderPort {

    private final OrderRepositoryJpa orderRepository;

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public boolean existsById(Long id) {
        return orderRepository.existsById(id);
    }

    @Override
    public Order getReferenceById(Long id) {
        return orderRepository.getReferenceById(id);
    }

    @Override
    public void delete(Order order) {
        orderRepository.delete(order);
    }
}

