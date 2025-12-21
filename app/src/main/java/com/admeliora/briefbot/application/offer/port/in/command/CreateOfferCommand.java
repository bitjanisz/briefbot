package com.admeliora.briefbot.application.offer.port.in.command;

public record CreateOfferCommand(
        Long clientId,
        Long briefingId,
        String currentStatus
) {
}

