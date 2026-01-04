package com.admeliora.briefbot.application.casestudy.port.in;

import com.admeliora.briefbot.application.casestudy.model.CaseStudy;
import com.admeliora.briefbot.application.casestudy.port.in.command.UpdateCaseStudyCommand;

public interface UpdateCaseStudyPort {
    CaseStudy update(UpdateCaseStudyCommand command);
}

