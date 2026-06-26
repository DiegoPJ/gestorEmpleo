package com.gestorempleo.application.domain.model;

import java.time.LocalDateTime;

public record Company(
        Long id,
        String name,
        String offerTitle,
        String contactName,
        ContactType contactType,
        String offerComment,
        String recruiterProcessNotes,
        String consultancyProcessNotes,
        String finalClientProcessNotes,
        LocalDateTime createdAt
) {
    public Company {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Company name is required");
        }
    }
}
