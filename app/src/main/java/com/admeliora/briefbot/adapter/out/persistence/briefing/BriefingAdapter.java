package com.admeliora.briefbot.adapter.out.persistence.briefing;

import com.admeliora.briefbot.adapter.out.persistence.briefing.jpa.BriefingRepositoryJpa;
import com.admeliora.briefbot.application.briefing.model.Briefing;
import com.admeliora.briefbot.application.briefing.port.out.BriefingPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BriefingAdapter implements BriefingPort {

    private final BriefingRepositoryJpa briefingRepository;

    @Override
    public Briefing save(Briefing briefing) {
        return briefingRepository.save(briefing);
    }

    @Override
    public Optional<Briefing> findById(Long id) {
        return briefingRepository.findById(id);
    }

    @Override
    public List<Briefing> findAll() {
        return briefingRepository.findAll();
    }

    @Override
    public boolean existsById(Long id) {
        return briefingRepository.existsById(id);
    }

    @Override
    public Briefing getReferenceById(Long id) {
        return briefingRepository.getReferenceById(id);
    }

    @Override
    public void delete(Briefing briefing) {
        briefingRepository.delete(briefing);
    }
}

