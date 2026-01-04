package com.admeliora.briefbot.application.client.port.in;

import com.admeliora.briefbot.application.client.model.Client;
import com.admeliora.briefbot.application.client.port.in.command.CreateClientCommand;

public interface CreateClientPort {
    Client create(CreateClientCommand command);
}

