package com.gestorempleo.application.domain.port.in;

import java.time.LocalDate;
import java.time.LocalTime;

public record UpdateInterviewCommand(
        Long id,
        Long companyId,
        String type,
        LocalDate interviewDate,
        LocalTime interviewTime,
        String status,
        String notes
) {
}
