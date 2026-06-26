package com.gestorempleo.application.domain.port.out;

import com.gestorempleo.application.domain.model.Company;

import java.util.List;

public interface CompanyRepositoryPort {

    Company save(Company company);

    List<Company> findAll();
}
