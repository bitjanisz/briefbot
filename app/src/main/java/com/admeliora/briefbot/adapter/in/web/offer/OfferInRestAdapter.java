package com.admeliora.briefbot.adapter.in.web.offer;

import com.admeliora.briefbot.adapter.in.web.offer.mapper.OfferMapper;
import com.admeliora.briefbot.adapter.in.web.offer.model.request.OfferCreateRequest;
import com.admeliora.briefbot.adapter.in.web.offer.model.request.OfferUpdateRequest;
import com.admeliora.briefbot.adapter.in.web.offer.model.response.OfferResponse;
import com.admeliora.briefbot.application.offer.port.in.*;
import com.admeliora.briefbot.application.offer.port.in.command.CreateOfferCommand;
import com.admeliora.briefbot.application.offer.port.in.command.UpdateOfferCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("offers")
@RequiredArgsConstructor
@Tag(name = "Offers", description = "CRUD operations for offers")
public class OfferInRestAdapter {

    private final CreateOfferPort createOfferPort;
    private final GetOfferPort getOfferPort;
    private final UpdateOfferPort updateOfferPort;
    private final DeleteOfferPort deleteOfferPort;
    private final ListOffersPort listOffersPort;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create offer", description = "Creates a new offer")
    public OfferResponse create(@Valid @RequestBody OfferCreateRequest request) {
        var command = new CreateOfferCommand(
                request.clientId(),
                request.briefingId(),
                request.currentStatus()
        );
        var offer = createOfferPort.create(command);
        return OfferMapper.toResponse(offer);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get offer by id", description = "Retrieves an offer by its ID")
    public ResponseEntity<OfferResponse> getById(@PathVariable Long id) {
        var offer = getOfferPort.getById(id);
        return offer
                .map(o -> ResponseEntity.ok(OfferMapper.toResponse(o)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update offer", description = "Updates an existing offer")
    public OfferResponse update(@PathVariable Long id, @Valid @RequestBody OfferUpdateRequest request) {
        var command = new UpdateOfferCommand(
                request.id(),
                request.currentStatus()
        );
        var offer = updateOfferPort.update(command);
        return OfferMapper.toResponse(offer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete offer", description = "Deletes an offer by ID")
    public void delete(@PathVariable Long id) {
        deleteOfferPort.delete(id);
    }

    @GetMapping
    @Operation(summary = "List offers by account", description = "Retrieves all offers for a specific account")
    public List<OfferResponse> listByAccountId(@RequestParam Long accountId) {
        return listOffersPort.listByAccountId(accountId)
                .stream()
                .map(OfferMapper::toResponse)
                .toList();
    }
}

