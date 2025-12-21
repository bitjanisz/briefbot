package com.admeliora.briefbot.application.service.service;

import com.admeliora.briefbot.application.service.port.out.ServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteServiceService implements com.admeliora.briefbot.application.service.port.in.DeleteServiceUseCase {
    private final ServicePort servicePort;

    @Override
    public void delete(Long id) {
        servicePort.deleteById(id);
    }
}

