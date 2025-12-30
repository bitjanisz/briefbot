package com.admeliora.briefbot.application.briefing.service;

import com.admeliora.briefbot.application.briefing.model.Briefing;
import com.admeliora.briefbot.application.briefing.port.in.DeleteBriefingPort;
import com.admeliora.briefbot.application.briefing.port.out.BriefingPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteBriefingService implements DeleteBriefingPort {

    private final BriefingPort briefingPort;

    @Override
    @Transactional
    public void delete(Long id) {
        Briefing briefing = briefingPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Briefing not found: " + id));
        briefingPort.delete(briefing);
    }
}

