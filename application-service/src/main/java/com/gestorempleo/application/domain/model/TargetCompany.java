package com.gestorempleo.application.domain.model;

import java.time.LocalDateTime;

public record TargetCompany(
        Long id,
        String name,
        boolean recruiterContacted,
        boolean companyMemberContacted,
        boolean cvSent,
        boolean replied,
        boolean waiting,
        LocalDateTime createdAt
) {
    public TargetCompany {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Company name is required");
        }
    }
}
