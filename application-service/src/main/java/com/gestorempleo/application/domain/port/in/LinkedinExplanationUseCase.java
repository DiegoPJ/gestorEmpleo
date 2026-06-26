package com.gestorempleo.application.domain.port.in;

import com.gestorempleo.application.domain.model.LinkedinExplanation;

import java.util.List;

public interface LinkedinExplanationUseCase {

    LinkedinExplanation create(CreateLinkedinExplanationCommand command);

    List<LinkedinExplanation> findAll();

    LinkedinExplanation update(UpdateLinkedinExplanationCommand command);

    void deleteById(Long id);
}
