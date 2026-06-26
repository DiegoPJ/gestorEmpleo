package com.gestorempleo.application.infrastructure.adapter.out.persistence;

import com.gestorempleo.application.domain.model.Company;
import com.gestorempleo.application.domain.port.out.CompanyRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyPersistenceAdapter implements CompanyRepositoryPort {

    private final CompanyJpaRepository companyJpaRepository;

    public CompanyPersistenceAdapter(CompanyJpaRepository companyJpaRepository) {
        this.companyJpaRepository = companyJpaRepository;
    }

    @Override
    public Company save(Company company) {
        var savedCompany = companyJpaRepository.save(toEntity(company));
        return toDomain(savedCompany);
    }

    @Override
    public List<Company> findAll() {
        return companyJpaRepository.findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    private CompanyJpaEntity toEntity(Company company) {
        return new CompanyJpaEntity(company.id(), company.name(), company.sector(), company.website());
    }

    private Company toDomain(CompanyJpaEntity entity) {
        return new Company(entity.getId(), entity.getName(), entity.getSector(), entity.getWebsite());
    }
}
