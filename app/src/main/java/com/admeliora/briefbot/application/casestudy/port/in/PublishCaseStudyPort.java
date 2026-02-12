package com.admeliora.briefbot.application.casestudy.port.in;

import com.admeliora.briefbot.application.casestudy.model.CaseStudy;

public interface PublishCaseStudyPort {
    CaseStudy publish(Long id);
}
