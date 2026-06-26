package com.gestorempleo.application.domain.port.in;

public record UpdateTargetCompanyCommand(
        Long id,
        String name,
        boolean recruiterContacted,
        boolean companyMemberContacted,
        boolean cvSent,
        boolean replied,
        boolean waiting
) {
}
