package com.admeliora.briefbot.application.order.service;

import com.admeliora.briefbot.application.account.port.out.AccountPort;
import com.admeliora.briefbot.application.common.exception.NoAccountAssignedException;
import com.admeliora.briefbot.application.offer.port.out.OfferVersionPort;
import com.admeliora.briefbot.application.order.model.Order;
import com.admeliora.briefbot.application.order.port.in.CreateOrderPort;
import com.admeliora.briefbot.application.order.port.in.command.CreateOrderCommand;
import com.admeliora.briefbot.application.order.port.out.OrderPort;
import com.admeliora.briefbot.infrastructure.context.AccountFilterContext;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateOrderService implements CreateOrderPort {

    private final OrderPort orderPort;
    private final AccountPort accountPort;
    private final OfferVersionPort offerVersionPort;
    private final AccountFilterContext accountFilterContext;

    @Override
    @Transactional
    public Order create(CreateOrderCommand command) {
        if (accountFilterContext.getAccountId() == null) {
            throw new NoAccountAssignedException(accountFilterContext.getUserEmail());
        }
        if (!accountPort.existsById(accountFilterContext.getAccountId())) {
            throw new EntityNotFoundException("Account not found: " + accountFilterContext.getAccountId());
        }
        if (!offerVersionPort.existsById(command.offerVersionId())) {
            throw new EntityNotFoundException("OfferVersion not found: " + command.offerVersionId());
        }

        Order order = Order.builder()
                .accountId(accountFilterContext.getAccountId())
                .offerVersionId(command.offerVersionId())
                .contractStatus(command.contractStatus())
                .contractFileUrl(command.contractFileUrl())
                .signedAt(command.signedAt())
                .build();

        return orderPort.save(order);
    }
}

