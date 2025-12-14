package com.admeliora.briefbot.service.usecase;

import com.admeliora.briefbot.service.model.Service;
import com.admeliora.briefbot.service.port.in.ListServicesPort;
import com.admeliora.briefbot.service.port.out.ServicePort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ListServicesUseCase implements ListServicesPort {
    private final ServicePort servicePort;

    @Override
    public List<Service> listByAccountId(Long accountId) {
        return servicePort.findByAccountId(accountId);
    }
}

