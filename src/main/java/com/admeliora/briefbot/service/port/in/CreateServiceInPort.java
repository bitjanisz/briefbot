package com.admeliora.briefbot.service.port.in;

import com.admeliora.briefbot.domain.service.Service;

public interface CreateServiceInPort {
    Service create(String name, String description, Long accountId);
}

