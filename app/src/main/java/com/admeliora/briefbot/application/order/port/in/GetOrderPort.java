package com.admeliora.briefbot.application.order.port.in;

import com.admeliora.briefbot.application.order.model.Order;

import java.util.Optional;

public interface GetOrderPort {
    Optional<Order> getById(Long id);
}

