package com.admeliora.briefbot.service.port.in;

import com.admeliora.briefbot.service.model.Service;
import com.admeliora.briefbot.service.port.in.command.UpdateServiceCommand;

public interface UpdateServicePort {
    Service update(UpdateServiceCommand command);
}

