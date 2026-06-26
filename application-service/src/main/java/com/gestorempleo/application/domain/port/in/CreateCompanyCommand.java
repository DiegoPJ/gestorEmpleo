package com.gestorempleo.application.domain.port.in;

public record CreateCompanyCommand(
        String name,
        String sector,
        String website
) {
}
