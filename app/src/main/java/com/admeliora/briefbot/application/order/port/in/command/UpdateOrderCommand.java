package com.admeliora.briefbot.application.order.port.in.command;

import java.time.LocalDateTime;

public record UpdateOrderCommand(
        Long id,
        String contractStatus,
        String contractFileUrl,
        LocalDateTime signedAt
) {
}

