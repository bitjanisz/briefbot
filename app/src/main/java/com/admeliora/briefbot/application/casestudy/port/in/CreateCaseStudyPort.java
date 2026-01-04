package com.admeliora.briefbot.application.casestudy.port.in;

import com.admeliora.briefbot.application.casestudy.model.CaseStudy;
import com.admeliora.briefbot.application.casestudy.port.in.command.CreateCaseStudyCommand;

public interface CreateCaseStudyPort {
    CaseStudy create(CreateCaseStudyCommand command);
}

