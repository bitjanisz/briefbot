package com.admeliora.briefbot.service.port.in;

import com.admeliora.briefbot.domain.service.Service;
import java.util.Optional;

public interface GetServiceInPort {
    Optional<Service> getById(Long id);
}

