package com.gestorempleo.application.infrastructure.adapter.out.persistence;

import com.gestorempleo.application.domain.model.Interview;
import com.gestorempleo.application.domain.port.out.InterviewRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InterviewPersistenceAdapter implements InterviewRepositoryPort {

    private final InterviewJpaRepository interviewJpaRepository;

    public InterviewPersistenceAdapter(InterviewJpaRepository interviewJpaRepository) {
        this.interviewJpaRepository = interviewJpaRepository;
    }

    @Override
    public Interview save(Interview interview) {
        var savedInterview = interviewJpaRepository.save(toEntity(interview));
        return toDomain(savedInterview);
    }

    @Override
    public List<Interview> findAll() {
        return interviewJpaRepository.findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<Interview> findById(Long id) {
        return interviewJpaRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public boolean existsByCompanyIdAndType(Long companyId, String type) {
        return interviewJpaRepository.existsByCompanyIdAndType(companyId, type);
    }

    @Override
    public boolean existsByCompanyIdAndTypeAndIdNot(Long companyId, String type, Long id) {
        return interviewJpaRepository.existsByCompanyIdAndTypeAndIdNot(companyId, type, id);
    }

    @Override
    public void deleteById(Long id) {
        interviewJpaRepository.deleteById(id);
    }

    private InterviewJpaEntity toEntity(Interview interview) {
        return new InterviewJpaEntity(
                interview.id(),
                interview.companyId(),
                interview.type(),
                interview.interviewDate(),
                interview.interviewTime(),
                interview.status(),
                interview.notes(),
                interview.createdAt()
        );
    }

    private Interview toDomain(InterviewJpaEntity entity) {
        return new Interview(
                entity.getId(),
                entity.getCompanyId(),
                entity.getType(),
                entity.getInterviewDate(),
                entity.getInterviewTime(),
                entity.getStatus(),
                entity.getNotes(),
                entity.getCreatedAt()
        );
    }
}
