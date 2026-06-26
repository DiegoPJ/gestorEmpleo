package com.gestorempleo.application.infrastructure.adapter.out.persistence;

import com.gestorempleo.application.domain.model.LinkedinExplanation;
import com.gestorempleo.application.domain.port.out.LinkedinExplanationRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LinkedinExplanationPersistenceAdapter implements LinkedinExplanationRepositoryPort {

    private final LinkedinExplanationJpaRepository linkedinExplanationJpaRepository;

    public LinkedinExplanationPersistenceAdapter(LinkedinExplanationJpaRepository linkedinExplanationJpaRepository) {
        this.linkedinExplanationJpaRepository = linkedinExplanationJpaRepository;
    }

    @Override
    public LinkedinExplanation save(LinkedinExplanation explanation) {
        return toDomain(linkedinExplanationJpaRepository.save(toEntity(explanation)));
    }

    @Override
    public List<LinkedinExplanation> findAll() {
        return linkedinExplanationJpaRepository.findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<LinkedinExplanation> findById(Long id) {
        return linkedinExplanationJpaRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        linkedinExplanationJpaRepository.deleteById(id);
    }

    private LinkedinExplanationJpaEntity toEntity(LinkedinExplanation explanation) {
        return new LinkedinExplanationJpaEntity(
                explanation.id(),
                explanation.question(),
                explanation.answer(),
                explanation.createdAt()
        );
    }

    private LinkedinExplanation toDomain(LinkedinExplanationJpaEntity entity) {
        return new LinkedinExplanation(
                entity.getId(),
                entity.getQuestion(),
                entity.getAnswer(),
                entity.getCreatedAt()
        );
    }
}
