package com.admeliora.briefbot.service.port.in;

import com.admeliora.briefbot.domain.service.Service;
import com.admeliora.briefbot.service.port.in.command.CreateServiceCommand;

public interface CreateServiceInPort {
    Service create(CreateServiceCommand command);
}
