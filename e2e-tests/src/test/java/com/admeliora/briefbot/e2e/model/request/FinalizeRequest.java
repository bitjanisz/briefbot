package com.admeliora.briefbot.e2e.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FinalizeRequest {
    private boolean finalized;
}
