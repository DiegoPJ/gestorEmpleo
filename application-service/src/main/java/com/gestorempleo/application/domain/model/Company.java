package com.gestorempleo.application.domain.model;

public record Company(
        Long id,
        String name,
        String sector,
        String website
) {
    public Company {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Company name is required");
        }
    }
}
