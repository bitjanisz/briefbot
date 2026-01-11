package com.admeliora.briefbot.e2e.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientResponse {
    private Long id;
    private Long accountId;
    private String name;
    private String email;
    private String companyName;
    private String industry;
    private LocalDateTime createdAt;
}

