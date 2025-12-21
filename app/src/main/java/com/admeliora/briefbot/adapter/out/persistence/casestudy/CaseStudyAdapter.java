package com.admeliora.briefbot.adapter.out.persistence.casestudy;

import com.admeliora.briefbot.adapter.out.persistence.casestudy.jpa.CaseStudyRepositoryJpa;
import com.admeliora.briefbot.application.casestudy.model.CaseStudy;
import com.admeliora.briefbot.application.casestudy.port.out.CaseStudyPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CaseStudyAdapter implements CaseStudyPort {

    private final CaseStudyRepositoryJpa caseStudyRepository;

    @Override
    public CaseStudy save(CaseStudy caseStudy) {
        return caseStudyRepository.save(caseStudy);
    }

    @Override
    public Optional<CaseStudy> findById(Long id) {
        return caseStudyRepository.findById(id);
    }

    @Override
    public List<CaseStudy> findAll() {
        return caseStudyRepository.findAll();
    }

    @Override
    public boolean existsById(Long id) {
        return caseStudyRepository.existsById(id);
    }

    @Override
    public CaseStudy getReferenceById(Long id) {
        return caseStudyRepository.getReferenceById(id);
    }

    @Override
    public void delete(CaseStudy caseStudy) {
        caseStudyRepository.delete(caseStudy);
    }
}

