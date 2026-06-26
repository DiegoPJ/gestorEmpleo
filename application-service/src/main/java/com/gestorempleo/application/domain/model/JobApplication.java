package com.gestorempleo.application.domain.model;

import java.time.LocalDate;

public record JobApplication(
        Long id,
        String company,
        String position,
        ApplicationStatus status,
        String contact,
        LocalDate appliedAt
) {
}
