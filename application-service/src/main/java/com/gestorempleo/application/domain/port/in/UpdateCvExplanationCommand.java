package com.gestorempleo.application.domain.port.in;

public record UpdateCvExplanationCommand(Long id, String question, String answer) {
}
