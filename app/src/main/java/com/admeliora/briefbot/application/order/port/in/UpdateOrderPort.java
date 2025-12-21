package com.admeliora.briefbot.application.order.port.in;

import com.admeliora.briefbot.application.order.model.Order;
import com.admeliora.briefbot.application.order.port.in.command.UpdateOrderCommand;

public interface UpdateOrderPort {
    Order update(UpdateOrderCommand command);
}

