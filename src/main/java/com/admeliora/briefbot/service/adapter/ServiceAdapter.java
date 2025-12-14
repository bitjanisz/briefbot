package com.admeliora.briefbot.service.adapter;

import com.admeliora.briefbot.adapter.out.persistence.service.ServiceRepositoryJpa;
import com.admeliora.briefbot.domain.service.Service;
import com.admeliora.briefbot.service.port.out.ServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ServiceAdapter implements ServicePort {

    private final ServiceRepositoryJpa serviceRepository;

    @Override
    public Service save(Service service) {
        return serviceRepository.save(service);
    }

    @Override
    public Optional<Service> findById(Long id) {
        return serviceRepository.findById(id);
    }

    @Override
    public List<Service> findByAccountId(Long accountId) {
        return serviceRepository.findByAccountId(accountId);
    }

    @Override
    public void deleteById(Long id) {
        serviceRepository.deleteById(id);
    }
}
