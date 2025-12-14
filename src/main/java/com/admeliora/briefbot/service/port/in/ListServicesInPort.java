package com.admeliora.briefbot.service.port.in;

import com.admeliora.briefbot.domain.service.Service;

import java.util.List;

public interface ListServicesInPort {
    List<Service> listByAccountId(Long accountId);
}

