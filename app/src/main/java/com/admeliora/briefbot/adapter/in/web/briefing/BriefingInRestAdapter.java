package com.admeliora.briefbot.adapter.in.web.briefing;

import com.admeliora.briefbot.adapter.in.web.briefing.mapper.BriefingMapper;
import com.admeliora.briefbot.adapter.in.web.briefing.model.request.BriefingCreateRequest;
import com.admeliora.briefbot.adapter.in.web.briefing.model.request.BriefingUpdateRequest;
import com.admeliora.briefbot.adapter.in.web.briefing.model.response.BriefingResponse;
import com.admeliora.briefbot.application.briefing.port.in.*;
import com.admeliora.briefbot.application.briefing.port.in.command.CreateBriefingCommand;
import com.admeliora.briefbot.application.briefing.port.in.command.UpdateBriefingCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("briefings")
@RequiredArgsConstructor
@Tag(name = "Briefings", description = "CRUD operations for briefings")
public class BriefingInRestAdapter {

    private final CreateBriefingPort createBriefingPort;
    private final GetBriefingPort getBriefingPort;
    private final UpdateBriefingPort updateBriefingPort;
    private final DeleteBriefingPort deleteBriefingPort;
    private final ListBriefingsPort listBriefingsPort;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create briefing", description = "Creates a new briefing")
    public BriefingResponse create(@Valid @RequestBody BriefingCreateRequest request) {
        var command = new CreateBriefingCommand(
                request.clientId(),
                request.status()
        );
        var briefing = createBriefingPort.create(command);
        return BriefingMapper.toResponse(briefing);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get briefing by id", description = "Retrieves a briefing by its ID")
    public ResponseEntity<BriefingResponse> getById(@PathVariable Long id) {
        var briefing = getBriefingPort.getById(id);
        return briefing
                .map(b -> ResponseEntity.ok(BriefingMapper.toResponse(b)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update briefing", description = "Updates an existing briefing")
    public BriefingResponse update(@PathVariable Long id, @Valid @RequestBody BriefingUpdateRequest request) {
        var command = new UpdateBriefingCommand(
                request.id(),
                request.status()
        );
        var briefing = updateBriefingPort.update(command);
        return BriefingMapper.toResponse(briefing);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete briefing", description = "Deletes a briefing by ID")
    public void delete(@PathVariable Long id) {
        deleteBriefingPort.delete(id);
    }

    @GetMapping
    @Operation(summary = "List briefings by account", description = "Retrieves all briefings for a specific account")
    public List<BriefingResponse> listByAccountId(@RequestParam Long accountId) {
        return listBriefingsPort.listByAccountId(accountId)
                .stream()
                .map(BriefingMapper::toResponse)
                .toList();
    }
}

