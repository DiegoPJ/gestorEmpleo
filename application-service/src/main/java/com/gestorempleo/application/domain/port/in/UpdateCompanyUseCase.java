package com.gestorempleo.application.domain.port.in;

import com.gestorempleo.application.domain.model.Company;

public interface UpdateCompanyUseCase {

    Company update(UpdateCompanyCommand command);
}
