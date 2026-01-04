package com.admeliora.briefbot.application.service.port.in;

import com.admeliora.briefbot.application.service.model.Service;

import java.util.List;

public interface ListServicesUseCase {
    List<Service> listByAccountId(Long accountId);
}

