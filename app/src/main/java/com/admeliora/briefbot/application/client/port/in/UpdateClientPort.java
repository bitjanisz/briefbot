package com.admeliora.briefbot.application.client.port.in;

import com.admeliora.briefbot.application.client.model.Client;
import com.admeliora.briefbot.application.client.port.in.command.UpdateClientCommand;

public interface UpdateClientPort {
    Client update(UpdateClientCommand command);
}

