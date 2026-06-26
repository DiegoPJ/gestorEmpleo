package com.gestorempleo.application.infrastructure.adapter.out.persistence;

import com.gestorempleo.application.domain.model.CvExplanation;
import com.gestorempleo.application.domain.port.out.CvExplanationRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CvExplanationPersistenceAdapter implements CvExplanationRepositoryPort {

    private final CvExplanationJpaRepository cvExplanationJpaRepository;

    public CvExplanationPersistenceAdapter(CvExplanationJpaRepository cvExplanationJpaRepository) {
        this.cvExplanationJpaRepository = cvExplanationJpaRepository;
    }

    @Override
    public CvExplanation save(CvExplanation explanation) {
        var savedExplanation = cvExplanationJpaRepository.save(toEntity(explanation));
        return toDomain(savedExplanation);
    }

    @Override
    public List<CvExplanation> findAll() {
        return cvExplanationJpaRepository.findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<CvExplanation> findById(Long id) {
        return cvExplanationJpaRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        cvExplanationJpaRepository.deleteById(id);
    }

    private CvExplanationJpaEntity toEntity(CvExplanation explanation) {
        return new CvExplanationJpaEntity(
                explanation.id(),
                explanation.question(),
                explanation.answer(),
                explanation.createdAt()
        );
    }

    private CvExplanation toDomain(CvExplanationJpaEntity entity) {
        return new CvExplanation(
                entity.getId(),
                entity.getQuestion(),
                entity.getAnswer(),
                entity.getCreatedAt()
        );
    }
}
