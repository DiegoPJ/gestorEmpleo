package com.gestorempleo.application.application.service;

import com.gestorempleo.application.domain.model.Company;
import com.gestorempleo.application.domain.port.in.CreateCompanyCommand;
import com.gestorempleo.application.domain.port.in.CreateCompanyUseCase;
import com.gestorempleo.application.domain.port.in.FindCompaniesUseCase;
import com.gestorempleo.application.domain.port.out.CompanyRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompanyService implements CreateCompanyUseCase, FindCompaniesUseCase {

    private final CompanyRepositoryPort companyRepositoryPort;

    public CompanyService(CompanyRepositoryPort companyRepositoryPort) {
        this.companyRepositoryPort = companyRepositoryPort;
    }

    @Override
    @Transactional
    public Company create(CreateCompanyCommand command) {
        var company = new Company(null, command.name(), command.sector(), command.website());
        return companyRepositoryPort.save(company);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Company> findAll() {
        return companyRepositoryPort.findAll();
    }
}
