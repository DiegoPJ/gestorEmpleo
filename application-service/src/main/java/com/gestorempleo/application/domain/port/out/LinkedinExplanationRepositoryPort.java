package com.gestorempleo.application.domain.port.out;

import com.gestorempleo.application.domain.model.LinkedinExplanation;

import java.util.List;
import java.util.Optional;

public interface LinkedinExplanationRepositoryPort {

    LinkedinExplanation save(LinkedinExplanation explanation);

    List<LinkedinExplanation> findAll();

    Optional<LinkedinExplanation> findById(Long id);

    void deleteById(Long id);
}
