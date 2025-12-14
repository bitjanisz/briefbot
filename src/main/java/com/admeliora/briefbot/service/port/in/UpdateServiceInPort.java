package com.admeliora.briefbot.service.port.in;

import com.admeliora.briefbot.domain.service.Service;
import com.admeliora.briefbot.service.port.in.command.UpdateServiceCommand;

public interface UpdateServiceInPort {
    Service update(UpdateServiceCommand command);
}
