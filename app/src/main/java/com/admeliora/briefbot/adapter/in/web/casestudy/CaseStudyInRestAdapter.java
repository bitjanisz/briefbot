package com.admeliora.briefbot.adapter.in.web.casestudy;

import com.admeliora.briefbot.adapter.in.web.casestudy.mapper.CaseStudyMapper;
import com.admeliora.briefbot.adapter.in.web.casestudy.model.request.CaseStudyCreateRequest;
import com.admeliora.briefbot.adapter.in.web.casestudy.model.request.CaseStudyUpdateRequest;
import com.admeliora.briefbot.adapter.in.web.casestudy.model.response.CaseStudyResponse;
import com.admeliora.briefbot.application.casestudy.port.in.*;
import com.admeliora.briefbot.application.casestudy.port.in.command.CreateCaseStudyCommand;
import com.admeliora.briefbot.application.casestudy.port.in.command.UpdateCaseStudyCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("case-studies")
@RequiredArgsConstructor
@Tag(name = "Case Studies", description = "CRUD operations for case studies")
public class CaseStudyInRestAdapter {

    private final CreateCaseStudyPort createCaseStudyPort;
    private final GetCaseStudyPort getCaseStudyPort;
    private final UpdateCaseStudyPort updateCaseStudyPort;
    private final DeleteCaseStudyPort deleteCaseStudyPort;
    private final ListCaseStudiesPort listCaseStudiesPort;
    private final PublishCaseStudyPort publishCaseStudyPort;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create case study", description = "Creates a new case study")
    public CaseStudyResponse create(@Valid @RequestBody CaseStudyCreateRequest request) {
        var command = CaseStudyMapper.toCreateCommand(request);
        var caseStudy = createCaseStudyPort.create(command);
        return CaseStudyMapper.toResponse(caseStudy);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get case study by id", description = "Retrieves a case study by its ID")
    public ResponseEntity<CaseStudyResponse> getById(@PathVariable Long id) {
        var caseStudy = getCaseStudyPort.getById(id);
        return caseStudy
                .map(cs -> ResponseEntity.ok(CaseStudyMapper.toResponse(cs)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update case study", description = "Updates an existing case study")
    public CaseStudyResponse update(@PathVariable Long id, @Valid @RequestBody CaseStudyUpdateRequest request) {
        var command = CaseStudyMapper.toUpdateCommand(request);
        var caseStudy = updateCaseStudyPort.update(command);
        return CaseStudyMapper.toResponse(caseStudy);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete case study", description = "Deletes a case study by ID")
    public void delete(@PathVariable Long id) {
        deleteCaseStudyPort.delete(id);
    }

    @GetMapping
    @Operation(summary = "List case studies by account", description = "Retrieves all case studies for a specific account")
    public List<CaseStudyResponse> listByAccountId(@RequestParam Long accountId) {
        return listCaseStudiesPort.listByAccountId(accountId)
                .stream()
                .map(CaseStudyMapper::toResponse)
                .toList();
    }

    @PutMapping("/{id}/publish")
    @Operation(summary = "Publish case study", description = "Publishes a case study by setting its status to PUBLISHED")
    public CaseStudyResponse publish(@PathVariable Long id) {
        var caseStudy = publishCaseStudyPort.publish(id);
        return CaseStudyMapper.toResponse(caseStudy);
    }
}
