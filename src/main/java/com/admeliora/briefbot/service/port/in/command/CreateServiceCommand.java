package com.admeliora.briefbot.service.port.in.command;

public record CreateServiceCommand(String name, String description, Long accountId) {}

