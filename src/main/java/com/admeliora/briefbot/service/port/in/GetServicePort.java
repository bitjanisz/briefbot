package com.admeliora.briefbot.service.port.in;

import com.admeliora.briefbot.service.model.Service;
import java.util.Optional;

public interface GetServicePort {
    Optional<Service> getById(Long id);
}

