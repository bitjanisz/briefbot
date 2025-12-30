package com.admeliora.briefbot.application.briefing.service;

import com.admeliora.briefbot.application.briefing.model.Briefing;
import com.admeliora.briefbot.application.briefing.port.in.UpdateBriefingPort;
import com.admeliora.briefbot.application.briefing.port.in.command.UpdateBriefingCommand;
import com.admeliora.briefbot.application.briefing.port.out.BriefingPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateBriefingService implements UpdateBriefingPort {

    private final BriefingPort briefingPort;

    @Override
    @Transactional
    public Briefing update(UpdateBriefingCommand command) {
        Briefing briefing = briefingPort.findById(command.id())
                .orElseThrow(() -> new EntityNotFoundException("Briefing not found: " + command.id()));

        briefing.setStatus(command.status());

        return briefingPort.save(briefing);
    }
}

