package com.admeliora.briefbot.service.port.in;

import com.admeliora.briefbot.domain.service.Service;

public interface UpdateServiceInPort {
    Service update(Long id, String name, String description);
}

