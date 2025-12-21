package com.admeliora.briefbot.application.briefing.port.in;

import com.admeliora.briefbot.application.briefing.model.Briefing;

import java.util.List;

public interface ListBriefingsPort {
    List<Briefing> listByAccountId(Long accountId);
}

