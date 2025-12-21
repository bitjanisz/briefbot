package com.admeliora.briefbot.application.order.port.in.command;

import java.time.LocalDateTime;

public record CreateOrderCommand(
        Long offerVersionId,
        String contractStatus,
        String contractFileUrl,
        LocalDateTime signedAt
) {
}

