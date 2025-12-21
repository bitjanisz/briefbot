package com.admeliora.briefbot.application.order.service;

import com.admeliora.briefbot.application.order.model.Order;
import com.admeliora.briefbot.application.order.port.in.UpdateOrderPort;
import com.admeliora.briefbot.application.order.port.in.command.UpdateOrderCommand;
import com.admeliora.briefbot.application.order.port.out.OrderPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateOrderService implements UpdateOrderPort {

    private final OrderPort orderPort;

    @Override
    @Transactional
    public Order update(UpdateOrderCommand command) {
        Order order = orderPort.findById(command.id())
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + command.id()));

        order.setContractStatus(command.contractStatus());
        order.setContractFileUrl(command.contractFileUrl());
        order.setSignedAt(command.signedAt());

        return orderPort.save(order);
    }
}

