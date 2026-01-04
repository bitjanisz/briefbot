package com.admeliora.briefbot.application.service.port.in;

import com.admeliora.briefbot.application.service.model.Service;
import com.admeliora.briefbot.application.service.port.in.command.CreateServiceCommand;

public interface CreateServiceUseCase {
    Service create(CreateServiceCommand command);
}

