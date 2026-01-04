package com.admeliora.briefbot.application.casestudy.port.in;

import com.admeliora.briefbot.application.casestudy.model.CaseStudy;

import java.util.List;

public interface ListCaseStudiesPort {
    List<CaseStudy> listByAccountId(Long accountId);
}

