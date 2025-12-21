package com.admeliora.briefbot.application.briefing.service;

import com.admeliora.briefbot.application.briefing.model.Briefing;
import com.admeliora.briefbot.application.briefing.port.in.ListBriefingsPort;
import com.admeliora.briefbot.application.briefing.port.out.BriefingPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListBriefingsService implements ListBriefingsPort {

    private final BriefingPort briefingPort;

    @Override
    @Transactional(readOnly = true)
    public List<Briefing> listByAccountId(Long accountId) {
        return briefingPort.findAll();
    }
}

