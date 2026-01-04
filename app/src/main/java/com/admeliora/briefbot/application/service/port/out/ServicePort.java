package com.admeliora.briefbot.application.service.port.out;

import com.admeliora.briefbot.application.service.model.Service;

import java.util.List;
import java.util.Optional;

public interface ServicePort {
    Service save(Service service);

    Optional<Service> findById(Long id);

    List<Service> findByAccountId(Long accountId);

    void deleteById(Long id);
}

