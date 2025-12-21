package com.admeliora.briefbot.adapter.in.web.order.mapper;

import com.admeliora.briefbot.adapter.in.web.order.model.response.OrderResponse;
import com.admeliora.briefbot.application.order.model.Order;

public class OrderMapper {
    public static OrderResponse toResponse(Order order) {
        if (order == null) return null;
        return new OrderResponse(
                order.getId(),
                order.getAccountId(),
                order.getOfferVersionId(),
                order.getContractStatus(),
                order.getContractFileUrl(),
                order.getSignedAt(),
                order.getCreatedAt()
        );
    }
}

