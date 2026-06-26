package com.gestorempleo.application.domain.port.in;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateInterviewCommand(
        Long companyId,
        String type,
        LocalDate interviewDate,
        LocalTime interviewTime,
        String status,
        String notes
) {
}
