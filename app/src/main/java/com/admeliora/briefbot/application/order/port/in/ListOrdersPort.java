package com.admeliora.briefbot.application.order.port.in;

import com.admeliora.briefbot.application.order.model.Order;

import java.util.List;

public interface ListOrdersPort {
    List<Order> listByAccountId(Long accountId);
}

