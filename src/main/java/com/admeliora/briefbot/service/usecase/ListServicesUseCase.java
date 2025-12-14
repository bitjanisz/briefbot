package com.admeliora.briefbot.service.usecase;

import com.admeliora.briefbot.domain.service.Service;
import com.admeliora.briefbot.service.port.in.ListServicesInPort;
import com.admeliora.briefbot.service.port.out.ServicePort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ListServicesUseCase implements ListServicesInPort {
    private final ServicePort servicePort;

    @Override
    public List<Service> listByAccountId(Long accountId) {
        return servicePort.findByAccountId(accountId);
    }
}

