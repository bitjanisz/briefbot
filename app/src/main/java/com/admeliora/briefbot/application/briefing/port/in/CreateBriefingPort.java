package com.admeliora.briefbot.application.briefing.port.in;

import com.admeliora.briefbot.application.briefing.model.Briefing;
import com.admeliora.briefbot.application.briefing.port.in.command.CreateBriefingCommand;

public interface CreateBriefingPort {
    Briefing create(CreateBriefingCommand command);
}

