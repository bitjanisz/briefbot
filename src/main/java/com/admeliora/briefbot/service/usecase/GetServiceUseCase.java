package com.admeliora.briefbot.service.usecase;

import com.admeliora.briefbot.domain.service.Service;
import com.admeliora.briefbot.service.port.in.GetServiceInPort;
import com.admeliora.briefbot.service.port.out.ServicePort;
import lombok.RequiredArgsConstructor;
import java.util.Optional;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class GetServiceUseCase implements GetServiceInPort {
    private final ServicePort servicePort;

    @Override
    public Optional<Service> getById(Long id) {
        return servicePort.findById(id);
    }
}

