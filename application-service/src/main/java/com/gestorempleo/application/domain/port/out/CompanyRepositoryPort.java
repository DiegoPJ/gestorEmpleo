package com.gestorempleo.application.domain.port.out;

import com.gestorempleo.application.domain.model.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyRepositoryPort {

    Company save(Company company);

    List<Company> findAll();

    Optional<Company> findById(Long id);

    void deleteById(Long id);
}
