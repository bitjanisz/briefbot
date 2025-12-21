package com.admeliora.briefbot.application.service.port.in.command;

public record CreateServiceCommand(String name, String description, Long accountId) {}

