package com.admeliora.briefbot.application.briefing.port.in;

import com.admeliora.briefbot.application.briefing.model.Briefing;

import java.util.Optional;

public interface GetBriefingPort {
    Optional<Briefing> getById(Long id);
}

