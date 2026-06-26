package com.gestorempleo.application.infrastructure.adapter.out.persistence;

import com.gestorempleo.application.domain.model.TargetCompany;
import com.gestorempleo.application.domain.port.out.TargetCompanyRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TargetCompanyPersistenceAdapter implements TargetCompanyRepositoryPort {

    private final TargetCompanyJpaRepository targetCompanyJpaRepository;

    public TargetCompanyPersistenceAdapter(TargetCompanyJpaRepository targetCompanyJpaRepository) {
        this.targetCompanyJpaRepository = targetCompanyJpaRepository;
    }

    @Override
    public TargetCompany save(TargetCompany company) {
        var savedCompany = targetCompanyJpaRepository.save(toEntity(company));
        return toDomain(savedCompany);
    }

    @Override
    public List<TargetCompany> findAll() {
        return targetCompanyJpaRepository.findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<TargetCompany> findById(Long id) {
        return targetCompanyJpaRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        targetCompanyJpaRepository.deleteById(id);
    }

    private TargetCompanyJpaEntity toEntity(TargetCompany company) {
        return new TargetCompanyJpaEntity(
                company.id(),
                company.name(),
                company.recruiterContacted(),
                company.companyMemberContacted(),
                company.cvSent(),
                company.replied(),
                company.waiting(),
                company.createdAt()
        );
    }

    private TargetCompany toDomain(TargetCompanyJpaEntity entity) {
        return new TargetCompany(
                entity.getId(),
                entity.getName(),
                entity.isRecruiterContacted(),
                entity.isCompanyMemberContacted(),
                entity.isCvSent(),
                entity.isReplied(),
                entity.isWaiting(),
                entity.getCreatedAt()
        );
    }
}
