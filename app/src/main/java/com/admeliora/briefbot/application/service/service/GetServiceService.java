package com.admeliora.briefbot.application.service.service;

import com.admeliora.briefbot.application.service.model.Service;
import com.admeliora.briefbot.application.service.port.out.ServicePort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class GetServiceService implements com.admeliora.briefbot.application.service.port.in.GetServiceUseCase {
    private final ServicePort servicePort;

    @Override
    public Optional<Service> getById(Long id) {
        return servicePort.findById(id);
    }
}
