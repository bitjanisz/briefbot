package com.admeliora.briefbot.application.offer.port.in.command;

public record UpdateOfferCommand(
        Long id,
        String currentStatus
) {
}

