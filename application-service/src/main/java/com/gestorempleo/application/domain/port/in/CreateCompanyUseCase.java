package com.gestorempleo.application.domain.port.in;

import com.gestorempleo.application.domain.model.Company;

public interface CreateCompanyUseCase {

    Company create(CreateCompanyCommand command);
}
