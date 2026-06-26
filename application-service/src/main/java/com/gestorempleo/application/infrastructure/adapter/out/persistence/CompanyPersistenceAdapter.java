package com.gestorempleo.application.infrastructure.adapter.out.persistence;

import com.gestorempleo.application.domain.model.Company;
import com.gestorempleo.application.domain.model.ContactType;
import com.gestorempleo.application.domain.port.out.CompanyRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Company> findById(Long id) {
        return companyJpaRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        companyJpaRepository.deleteById(id);
    }

    private CompanyJpaEntity toEntity(Company company) {
        var contactType = company.contactType() == null ? null : company.contactType().name();
        return new CompanyJpaEntity(
                company.id(),
                company.name(),
                company.offerTitle(),
                company.contactName(),
                contactType,
                company.offerComment(),
                company.recruiterProcessNotes(),
                company.consultancyProcessNotes(),
                company.finalClientProcessNotes(),
                company.createdAt()
        );
    }

    private Company toDomain(CompanyJpaEntity entity) {
        var contactType = entity.getContactType() == null ? null : ContactType.valueOf(entity.getContactType());
        return new Company(
                entity.getId(),
                entity.getName(),
                entity.getOfferTitle(),
                entity.getContactName(),
                contactType,
                entity.getOfferComment(),
                entity.getRecruiterProcessNotes(),
                entity.getConsultancyProcessNotes(),
                entity.getFinalClientProcessNotes(),
                entity.getCreatedAt()
        );
    }
}
