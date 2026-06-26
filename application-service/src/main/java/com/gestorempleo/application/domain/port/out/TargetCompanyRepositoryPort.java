package com.gestorempleo.application.domain.port.out;

import com.gestorempleo.application.domain.model.TargetCompany;

import java.util.List;
import java.util.Optional;

public interface TargetCompanyRepositoryPort {

    TargetCompany save(TargetCompany company);

    List<TargetCompany> findAll();

    Optional<TargetCompany> findById(Long id);

    void deleteById(Long id);
}
