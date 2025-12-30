package com.admeliora.briefbot.adapter.in.web.client;

import com.admeliora.briefbot.adapter.in.web.client.mapper.ClientMapper;
import com.admeliora.briefbot.adapter.in.web.client.model.request.ClientCreateRequest;
import com.admeliora.briefbot.adapter.in.web.client.model.request.ClientUpdateRequest;
import com.admeliora.briefbot.adapter.in.web.client.model.response.ClientResponse;
import com.admeliora.briefbot.application.client.port.in.*;
import com.admeliora.briefbot.application.client.port.in.command.CreateClientCommand;
import com.admeliora.briefbot.application.client.port.in.command.UpdateClientCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
@Tag(name = "Clients", description = "CRUD operations for clients")
public class ClientInRestAdapter {

    private final CreateClientPort createClientPort;
    private final GetClientPort getClientPort;
    private final UpdateClientPort updateClientPort;
    private final DeleteClientPort deleteClientPort;
    private final ListClientsPort listClientsPort;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create client", description = "Creates a new client")
    public ClientResponse create(@Valid @RequestBody ClientCreateRequest request) {
        var command = new CreateClientCommand(
                request.name(),
                request.email(),
                request.companyName(),
                request.industry()
        );
        var client = createClientPort.create(command);
        return ClientMapper.toResponse(client);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get client by id", description = "Retrieves a client by its ID")
    public ResponseEntity<ClientResponse> getById(@PathVariable Long id) {
        var client = getClientPort.getById(id);
        return client
                .map(c -> ResponseEntity.ok(ClientMapper.toResponse(c)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update client", description = "Updates an existing client")
    public ClientResponse update(@PathVariable Long id, @Valid @RequestBody ClientUpdateRequest request) {
        var command = new UpdateClientCommand(
                request.id(),
                request.name(),
                request.email(),
                request.companyName(),
                request.industry()
        );
        var client = updateClientPort.update(command);
        return ClientMapper.toResponse(client);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete client", description = "Deletes a client by ID")
    public void delete(@PathVariable Long id) {
        deleteClientPort.delete(id);
    }

    @GetMapping
    @Operation(summary = "List clients by account", description = "Retrieves all clients for a specific account")
    public List<ClientResponse> listByAccountId(@RequestParam Long accountId) {
        return listClientsPort.listByAccountId(accountId)
                .stream()
                .map(ClientMapper::toResponse)
                .toList();
    }
}

