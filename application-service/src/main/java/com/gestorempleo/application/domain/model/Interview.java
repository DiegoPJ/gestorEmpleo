package com.gestorempleo.application.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record Interview(
        Long id,
        Long companyId,
        String type,
        LocalDate interviewDate,
        LocalTime interviewTime,
        String status,
        String notes,
        LocalDateTime createdAt
) {
    public Interview {
        if (companyId == null) {
            throw new IllegalArgumentException("Company id is required");
        }

        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Interview type is required");
        }

        if (interviewDate == null) {
            throw new IllegalArgumentException("Interview date is required");
        }

        if (interviewTime == null) {
            throw new IllegalArgumentException("Interview time is required");
        }
    }
}
