package com.admeliora.briefbot.adapter.out.persistence.ai;

import com.admeliora.briefbot.adapter.out.persistence.casestudy.jpa.CaseStudyRepositoryJpa;
import com.admeliora.briefbot.adapter.out.persistence.service.jpa.ServiceRepositoryJpa;
import com.admeliora.briefbot.application.ai.model.CaseStudyWithServicesDto;
import com.admeliora.briefbot.application.ai.port.out.CaseStudyWithServicesPort;
import com.admeliora.briefbot.application.casestudy.model.CaseStudy;
import com.admeliora.briefbot.application.casestudy.model.CaseStudyService;
import com.admeliora.briefbot.application.service.model.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CaseStudyWithServicesAdapter implements CaseStudyWithServicesPort {

    private final CaseStudyRepositoryJpa caseStudyRepository;
    private final ServiceRepositoryJpa serviceRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<CaseStudyWithServicesDto> getCaseStudyWithServices(Long caseStudyId) {
        return caseStudyRepository.findById(caseStudyId)
                .map(this::mapToDto);
    }

    @Override
    public List<CaseStudyWithServicesDto> getAllCaseStudiesWithServices() {
        return caseStudyRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private CaseStudyWithServicesDto mapToDto(CaseStudy caseStudy) {
        // Get case study services with proper join
        List<CaseStudyService> caseStudyServices = entityManager
                .createQuery(
                        "SELECT cs FROM CaseStudyService cs WHERE cs.caseStudy.id = :caseStudyId",
                        CaseStudyService.class
                )
                .setParameter("caseStudyId", caseStudy.getId())
                .getResultList();

        List<CaseStudyWithServicesDto.ServiceDto> serviceDtos = caseStudyServices.stream()
                .map(css -> {
                    Service service = serviceRepository.findById(css.getServiceId())
                            .orElse(null);

                    if (service == null) {
                        return null;
                    }

                    BigDecimal discount = css.getDiscountPercentage() != null
                            ? css.getDiscountPercentage()
                            : BigDecimal.ZERO;

                    BigDecimal finalPrice = service.getBasePrice()
                            .multiply(BigDecimal.ONE.subtract(discount.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP)))
                            .setScale(2, RoundingMode.HALF_UP);

                    return new CaseStudyWithServicesDto.ServiceDto(
                            service.getId(),
                            service.getName(),
                            service.getDescription(),
                            service.getBasePrice(),
                            service.getCurrency(),
                            service.getPricingUnit(),
                            discount,
                            finalPrice
                    );
                })
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toList());

        return new CaseStudyWithServicesDto(
                caseStudy.getId(),
                caseStudy.getProjectName(),
                caseStudy.getClientIndustry(),
                caseStudy.getKeywords(),
                caseStudy.getScopeSummary(),
                caseStudy.getChallengesSolved(),
                caseStudy.getBudgetRangeEnum(),
                caseStudy.getStatus() != null && caseStudy.getStatus().name().equals("PUBLISHED"),
                caseStudy.getCreatedAt(),
                serviceDtos
        );
    }
}

