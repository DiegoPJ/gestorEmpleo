package com.gestorempleo.application.domain.port.in;

public record CreateCompanyCommand(
        String name,
        String offerTitle,
        String contactName,
        String contactType,
        String offerComment,
        String recruiterProcessNotes,
        String consultancyProcessNotes,
        String finalClientProcessNotes
) {
}
