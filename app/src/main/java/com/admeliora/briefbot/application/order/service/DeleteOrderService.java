package com.admeliora.briefbot.application.order.service;

import com.admeliora.briefbot.application.order.model.Order;
import com.admeliora.briefbot.application.order.port.in.DeleteOrderPort;
import com.admeliora.briefbot.application.order.port.out.OrderPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteOrderService implements DeleteOrderPort {

    private final OrderPort orderPort;

    @Override
    @Transactional
    public void delete(Long id) {
        Order order = orderPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + id));
        orderPort.delete(order);
    }
}

