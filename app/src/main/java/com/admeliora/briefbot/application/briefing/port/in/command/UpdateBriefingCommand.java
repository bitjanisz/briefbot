package com.admeliora.briefbot.application.briefing.port.in.command;

public record UpdateBriefingCommand(
        Long id,
        String status
) {
}

