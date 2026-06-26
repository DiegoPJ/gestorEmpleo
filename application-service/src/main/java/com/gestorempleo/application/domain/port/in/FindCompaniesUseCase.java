package com.gestorempleo.application.domain.port.in;

import com.gestorempleo.application.domain.model.Company;

import java.util.List;

public interface FindCompaniesUseCase {

    List<Company> findAll();
}
