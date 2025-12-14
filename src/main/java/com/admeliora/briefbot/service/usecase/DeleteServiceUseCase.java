package com.admeliora.briefbot.service.usecase;

import com.admeliora.briefbot.service.port.in.DeleteServiceInPort;
import com.admeliora.briefbot.service.port.out.ServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteServiceUseCase implements DeleteServiceInPort {
    private final ServicePort servicePort;

    @Override
    public void delete(Long id) {
        servicePort.deleteById(id);
    }
}

