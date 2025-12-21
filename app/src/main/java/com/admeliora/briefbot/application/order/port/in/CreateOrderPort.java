package com.admeliora.briefbot.application.order.port.in;

import com.admeliora.briefbot.application.order.model.Order;
import com.admeliora.briefbot.application.order.port.in.command.CreateOrderCommand;

public interface CreateOrderPort {
    Order create(CreateOrderCommand command);
}

