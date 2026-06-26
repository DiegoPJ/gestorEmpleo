package com.gestorempleo.application.domain.port.out;

import com.gestorempleo.application.domain.model.Interview;

import java.util.List;
import java.util.Optional;

public interface InterviewRepositoryPort {

    Interview save(Interview interview);

    List<Interview> findAll();

    Optional<Interview> findById(Long id);

    boolean existsByCompanyIdAndType(Long companyId, String type);

    boolean existsByCompanyIdAndTypeAndIdNot(Long companyId, String type, Long id);

    void deleteById(Long id);
}
