package com.admeliora.briefbot.service.port.in;

import com.admeliora.briefbot.service.model.Service;
import java.util.List;

public interface ListServicesPort {
    List<Service> listByAccountId(Long accountId);
}

