package com.admeliora.briefbot.application.briefing.port.in.command;

public record CreateBriefingCommand(
        Long clientId,
        String status
) {
}

