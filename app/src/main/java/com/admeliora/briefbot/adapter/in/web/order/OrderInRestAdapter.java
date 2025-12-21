package com.admeliora.briefbot.adapter.in.web.order;

import com.admeliora.briefbot.adapter.in.web.order.mapper.OrderMapper;
import com.admeliora.briefbot.adapter.in.web.order.model.request.OrderCreateRequest;
import com.admeliora.briefbot.adapter.in.web.order.model.request.OrderUpdateRequest;
import com.admeliora.briefbot.adapter.in.web.order.model.response.OrderResponse;
import com.admeliora.briefbot.application.order.port.in.*;
import com.admeliora.briefbot.application.order.port.in.command.CreateOrderCommand;
import com.admeliora.briefbot.application.order.port.in.command.UpdateOrderCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "CRUD operations for orders")
public class OrderInRestAdapter {

    private final CreateOrderPort createOrderPort;
    private final GetOrderPort getOrderPort;
    private final UpdateOrderPort updateOrderPort;
    private final DeleteOrderPort deleteOrderPort;
    private final ListOrdersPort listOrdersPort;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create order", description = "Creates a new order")
    public OrderResponse create(@Valid @RequestBody OrderCreateRequest request) {
        var command = new CreateOrderCommand(
                request.offerVersionId(),
                request.contractStatus(),
                request.contractFileUrl(),
                request.signedAt()
        );
        var order = createOrderPort.create(command);
        return OrderMapper.toResponse(order);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by id", description = "Retrieves an order by its ID")
    public ResponseEntity<OrderResponse> getById(@PathVariable Long id) {
        var order = getOrderPort.getById(id);
        return order
                .map(o -> ResponseEntity.ok(OrderMapper.toResponse(o)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update order", description = "Updates an existing order")
    public OrderResponse update(@PathVariable Long id, @Valid @RequestBody OrderUpdateRequest request) {
        var command = new UpdateOrderCommand(
                request.id(),
                request.contractStatus(),
                request.contractFileUrl(),
                request.signedAt()
        );
        var order = updateOrderPort.update(command);
        return OrderMapper.toResponse(order);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete order", description = "Deletes an order by ID")
    public void delete(@PathVariable Long id) {
        deleteOrderPort.delete(id);
    }

    @GetMapping
    @Operation(summary = "List orders by account", description = "Retrieves all orders for a specific account")
    public List<OrderResponse> listByAccountId(@RequestParam Long accountId) {
        return listOrdersPort.listByAccountId(accountId)
                .stream()
                .map(OrderMapper::toResponse)
                .toList();
    }
}

