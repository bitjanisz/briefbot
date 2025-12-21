package com.admeliora.briefbot.application.service.service;

import com.admeliora.briefbot.application.service.model.Service;
import com.admeliora.briefbot.application.service.port.out.ServicePort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ListServicesService implements com.admeliora.briefbot.application.service.port.in.ListServicesUseCase {
    private final ServicePort servicePort;

    @Override
    public List<Service> listByAccountId(Long accountId) {
        return servicePort.findByAccountId(accountId);
    }
}

