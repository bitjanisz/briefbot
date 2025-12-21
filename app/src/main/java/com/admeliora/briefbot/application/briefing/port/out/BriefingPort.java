package com.admeliora.briefbot.application.briefing.port.out;

import com.admeliora.briefbot.application.briefing.model.Briefing;

import java.util.List;
import java.util.Optional;

public interface BriefingPort {
    Briefing save(Briefing briefing);

    Optional<Briefing> findById(Long id);

    List<Briefing> findAll();

    boolean existsById(Long id);

    Briefing getReferenceById(Long id);

    void delete(Briefing briefing);
}

