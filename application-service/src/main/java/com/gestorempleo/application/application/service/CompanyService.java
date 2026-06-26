package com.gestorempleo.application.application.service;

import com.gestorempleo.application.domain.model.Company;
import com.gestorempleo.application.domain.model.ContactType;
import com.gestorempleo.application.domain.port.in.CreateCompanyCommand;
import com.gestorempleo.application.domain.port.in.CreateCompanyUseCase;
import com.gestorempleo.application.domain.port.in.DeleteCompanyUseCase;
import com.gestorempleo.application.domain.port.in.FindCompaniesUseCase;
import com.gestorempleo.application.domain.port.in.UpdateCompanyCommand;
import com.gestorempleo.application.domain.port.in.UpdateCompanyUseCase;
import com.gestorempleo.application.domain.port.out.CompanyRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CompanyService implements CreateCompanyUseCase, FindCompaniesUseCase, DeleteCompanyUseCase, UpdateCompanyUseCase {

    private final CompanyRepositoryPort companyRepositoryPort;

    public CompanyService(CompanyRepositoryPort companyRepositoryPort) {
        this.companyRepositoryPort = companyRepositoryPort;
    }

    @Override
    @Transactional
    public Company create(CreateCompanyCommand command) {
        var contactType = ContactType.valueOf(command.contactType());
        var company = new Company(
                null,
                command.name(),
                command.offerTitle(),
                command.contactName(),
                contactType,
                command.offerComment(),
                command.recruiterProcessNotes(),
                command.consultancyProcessNotes(),
                command.finalClientProcessNotes(),
                LocalDateTime.now()
        );
        return companyRepositoryPort.save(company);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Company> findAll() {
        return companyRepositoryPort.findAll();
    }

    @Override
    @Transactional
    public Company update(UpdateCompanyCommand command) {
        var currentCompany = companyRepositoryPort.findById(command.id())
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));
        var contactType = ContactType.valueOf(command.contactType());
        var company = new Company(
                currentCompany.id(),
                command.name(),
                command.offerTitle(),
                command.contactName(),
                contactType,
                command.offerComment(),
                command.recruiterProcessNotes(),
                command.consultancyProcessNotes(),
                command.finalClientProcessNotes(),
                currentCompany.createdAt()
        );
        return companyRepositoryPort.save(company);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        companyRepositoryPort.deleteById(id);
    }
}
