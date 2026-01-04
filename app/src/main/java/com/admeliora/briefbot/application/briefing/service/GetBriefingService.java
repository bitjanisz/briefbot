package com.admeliora.briefbot.application.briefing.service;

import com.admeliora.briefbot.application.briefing.model.Briefing;
import com.admeliora.briefbot.application.briefing.port.in.GetBriefingPort;
import com.admeliora.briefbot.application.briefing.port.out.BriefingPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetBriefingService implements GetBriefingPort {

    private final BriefingPort briefingPort;

    @Override
    @Transactional(readOnly = true)
    public Optional<Briefing> getById(Long id) {
        return briefingPort.findById(id);
    }
}

