package com.gestorempleo.application.domain.port.in;

import com.gestorempleo.application.domain.model.CvExplanation;

import java.util.List;

public interface CvExplanationUseCase {

    CvExplanation create(CreateCvExplanationCommand command);

    List<CvExplanation> findAll();

    CvExplanation update(UpdateCvExplanationCommand command);

    void deleteById(Long id);
}
