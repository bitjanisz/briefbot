package com.admeliora.briefbot.application.service.port.in;

import com.admeliora.briefbot.application.service.model.Service;
import com.admeliora.briefbot.application.service.port.in.command.UpdateServiceCommand;

public interface UpdateServiceUseCase {
    Service update(UpdateServiceCommand command);
}

