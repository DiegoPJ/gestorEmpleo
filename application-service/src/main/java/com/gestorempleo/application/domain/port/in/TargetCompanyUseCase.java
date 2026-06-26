package com.gestorempleo.application.domain.port.in;

import com.gestorempleo.application.domain.model.TargetCompany;

import java.util.List;

public interface TargetCompanyUseCase {

    TargetCompany create(CreateTargetCompanyCommand command);

    List<TargetCompany> findAll();

    TargetCompany update(UpdateTargetCompanyCommand command);

    void deleteById(Long id);
}
