package com.admeliora.briefbot.adapter.in.web.briefing.mapper;

import com.admeliora.briefbot.adapter.in.web.briefing.model.response.BriefingResponse;
import com.admeliora.briefbot.adapter.in.web.briefing.model.response.BriefingVersionResponse;
import com.admeliora.briefbot.application.briefing.model.Briefing;
import com.admeliora.briefbot.application.briefing.model.BriefingVersion;

import java.util.List;
import java.util.stream.Collectors;

public class BriefingMapper {

    public static BriefingResponse toResponse(Briefing briefing) {
        if (briefing == null) return null;

        List<BriefingVersionResponse> versions = briefing.getVersions() != null
                ? briefing.getVersions().stream()
                        .map(BriefingMapper::toVersionResponse)
                        .collect(Collectors.toList())
                : List.of();

        return new BriefingResponse(
                briefing.getId(),
                briefing.getAccountId(),
                briefing.getClientId(),
                briefing.getStatus(),
                briefing.getCreatedAt(),
                versions
        );
    }

    public static BriefingVersionResponse toVersionResponse(BriefingVersion version) {
        if (version == null) return null;
        return new BriefingVersionResponse(
                version.getId(),
                version.getBriefing() != null ? version.getBriefing().getId() : null,
                version.getVersionNumber(),
                version.getFormStructure(),
                version.getClientResponses(),
                version.getCreatedByUserId(),
                version.getCreatedAt()
        );
    }
}

