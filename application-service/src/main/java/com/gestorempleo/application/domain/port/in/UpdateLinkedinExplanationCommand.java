package com.gestorempleo.application.domain.port.in;

public record UpdateLinkedinExplanationCommand(Long id, String question, String answer) {
}
