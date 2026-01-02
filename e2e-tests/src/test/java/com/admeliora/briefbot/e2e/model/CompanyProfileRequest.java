package com.admeliora.briefbot.e2e.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyProfileRequest {
    private String name;
    private String description;
    private String website;
    private String address;
}
