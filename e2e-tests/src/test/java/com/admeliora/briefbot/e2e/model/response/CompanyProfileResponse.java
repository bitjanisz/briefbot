package com.admeliora.briefbot.e2e.model.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CompanyProfileResponse {
    private Long id;
    private String name;
    private String description;
    private String website;
    private String address;
    private LocalDateTime createdAt;
}
