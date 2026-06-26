package com.gestorempleo.application.domain.model;

import java.time.LocalDateTime;

public record LinkedinExplanation(
        Long id,
        String question,
        String answer,
        LocalDateTime createdAt
) {
    public LinkedinExplanation {
        if (question == null || question.isBlank()) {
            throw new IllegalArgumentException("Question is required");
        }

        if (answer == null || answer.isBlank()) {
            throw new IllegalArgumentException("Answer is required");
        }
    }
}
