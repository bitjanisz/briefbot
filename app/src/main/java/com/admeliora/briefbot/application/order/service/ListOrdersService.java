package com.admeliora.briefbot.application.order.service;

import com.admeliora.briefbot.application.order.model.Order;
import com.admeliora.briefbot.application.order.port.in.ListOrdersPort;
import com.admeliora.briefbot.application.order.port.out.OrderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListOrdersService implements ListOrdersPort {

    private final OrderPort orderPort;

    @Override
    @Transactional(readOnly = true)
    public List<Order> listByAccountId(Long accountId) {
        return orderPort.findAll();
    }
}

