package com.admeliora.briefbot.application.briefing.port.in;

import com.admeliora.briefbot.application.briefing.model.Briefing;
import com.admeliora.briefbot.application.briefing.port.in.command.UpdateBriefingCommand;

public interface UpdateBriefingPort {
    Briefing update(UpdateBriefingCommand command);
}

