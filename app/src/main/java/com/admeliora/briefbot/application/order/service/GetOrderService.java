package com.admeliora.briefbot.application.order.service;

import com.admeliora.briefbot.application.order.model.Order;
import com.admeliora.briefbot.application.order.port.in.GetOrderPort;
import com.admeliora.briefbot.application.order.port.out.OrderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetOrderService implements GetOrderPort {

    private final OrderPort orderPort;

    @Override
    @Transactional(readOnly = true)
    public Optional<Order> getById(Long id) {
        return orderPort.findById(id);
    }
}

