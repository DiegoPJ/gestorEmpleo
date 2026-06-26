package com.gestorempleo.application.domain.port.out;

import com.gestorempleo.application.domain.model.CvExplanation;

import java.util.List;
import java.util.Optional;

public interface CvExplanationRepositoryPort {

    CvExplanation save(CvExplanation explanation);

    List<CvExplanation> findAll();

    Optional<CvExplanation> findById(Long id);

    void deleteById(Long id);
}
