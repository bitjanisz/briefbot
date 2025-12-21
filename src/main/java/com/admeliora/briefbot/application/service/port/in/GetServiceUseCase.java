package com.admeliora.briefbot.application.service.port.in;

import com.admeliora.briefbot.application.service.model.Service;
import java.util.Optional;

public interface GetServiceUseCase {
    Optional<Service> getById(Long id);
}

