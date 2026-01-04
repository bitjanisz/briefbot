package com.admeliora.briefbot.adapter.in.web.companyprofile;

import com.admeliora.briefbot.adapter.in.web.companyprofile.mapper.CompanyProfileMapper;
import com.admeliora.briefbot.adapter.in.web.companyprofile.model.request.CompanyProfileCreateRequest;
import com.admeliora.briefbot.adapter.in.web.companyprofile.model.request.CompanyProfileUpdateRequest;
import com.admeliora.briefbot.adapter.in.web.companyprofile.model.response.CompanyProfileResponse;
import com.admeliora.briefbot.application.companyprofile.port.in.*;
import com.admeliora.briefbot.application.companyprofile.port.in.command.CreateCompanyProfileCommand;
import com.admeliora.briefbot.application.companyprofile.port.in.command.UpdateCompanyProfileCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("company-profiles")
@RequiredArgsConstructor
@Tag(name = "Company Profiles", description = "CRUD operations for company profiles")
public class CompanyProfileInRestAdapter {

    private final CreateCompanyProfilePort createCompanyProfilePort;
    private final GetCompanyProfilePort getCompanyProfilePort;
    private final UpdateCompanyProfilePort updateCompanyProfilePort;
    private final DeleteCompanyProfilePort deleteCompanyProfilePort;
    private final ListCompanyProfilesPort listCompanyProfilesPort;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create company profile", description = "Creates a new company profile")
    public CompanyProfileResponse create(@Valid @RequestBody CompanyProfileCreateRequest request) {
        var command = new CreateCompanyProfileCommand(
                request.companyLegalName(),
                request.taxId(),
                request.addressLine(),
                request.contactEmail(),
                request.contactPhone(),
                request.shortValueProposition(),
                request.coreValues(),
                request.aiToneStyle()
        );
        var profile = createCompanyProfilePort.create(command);
        return CompanyProfileMapper.toResponse(profile);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get company profile by id", description = "Retrieves a company profile by its ID")
    public ResponseEntity<CompanyProfileResponse> getById(@PathVariable Long id) {
        var profile = getCompanyProfilePort.getById(id);
        return profile
                .map(p -> ResponseEntity.ok(CompanyProfileMapper.toResponse(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update company profile", description = "Updates an existing company profile")
    public CompanyProfileResponse update(@PathVariable Long id, @Valid @RequestBody CompanyProfileUpdateRequest request) {
        var command = new UpdateCompanyProfileCommand(
                request.id(),
                request.companyLegalName(),
                request.taxId(),
                request.addressLine(),
                request.contactEmail(),
                request.contactPhone(),
                request.shortValueProposition(),
                request.coreValues(),
                request.aiToneStyle()
        );
        var profile = updateCompanyProfilePort.update(command);
        return CompanyProfileMapper.toResponse(profile);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete company profile", description = "Deletes a company profile by ID")
    public void delete(@PathVariable Long id) {
        deleteCompanyProfilePort.delete(id);
    }

    @GetMapping
    @Operation(summary = "List company profiles by account", description = "Retrieves all company profiles for a specific account")
    public List<CompanyProfileResponse> listByAccountId(@RequestParam Long accountId) {
        return listCompanyProfilesPort.listByAccountId(accountId)
                .stream()
                .map(CompanyProfileMapper::toResponse)
                .toList();
    }
}

