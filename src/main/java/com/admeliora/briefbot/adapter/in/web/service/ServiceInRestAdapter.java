package com.admeliora.briefbot.adapter.in.web.service;

import com.admeliora.briefbot.adapter.in.web.service.mapper.ServiceMapper;
import com.admeliora.briefbot.adapter.in.web.service.request.ServiceCreateRequest;
import com.admeliora.briefbot.adapter.in.web.service.request.ServiceUpdateRequest;
import com.admeliora.briefbot.adapter.in.web.service.response.ServiceResponse;
import com.admeliora.briefbot.service.port.in.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
@Tag(name = "Services", description = "CRUD for services")
public class ServiceInRestAdapter {
    private final CreateServiceInPort createServiceInPort;
    private final GetServiceInPort getServiceInPort;
    private final UpdateServiceInPort updateServiceInPort;
    private final DeleteServiceInPort deleteServiceInPort;
    private final ListServicesInPort listServicesInPort;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create service")
    public ServiceResponse create(@Valid @RequestBody ServiceCreateRequest dto) {
        var service = createServiceInPort.create(dto.name(), dto.description(), dto.accountId());
        return ServiceMapper.toResponse(service);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get service by id")
    public ResponseEntity<ServiceResponse> get(@PathVariable Long id) {
        return getServiceInPort.getById(id)
                .map(ServiceMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update service")
    public ServiceResponse update(@PathVariable Long id, @Valid @RequestBody ServiceUpdateRequest dto) {
        var service = updateServiceInPort.update(id, dto.name(), dto.description());
        return ServiceMapper.toResponse(service);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete service")
    public void delete(@PathVariable Long id) {
        deleteServiceInPort.delete(id);
    }

    @GetMapping
    @Operation(summary = "List services by account id")
    public List<ServiceResponse> list(@RequestParam Long accountId) {
        return listServicesInPort.listByAccountId(accountId)
                .stream()
                .map(ServiceMapper::toResponse)
                .toList();
    }
}
