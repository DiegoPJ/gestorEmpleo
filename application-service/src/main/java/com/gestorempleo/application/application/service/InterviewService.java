package com.gestorempleo.application.application.service;

import com.gestorempleo.application.domain.model.Interview;
import com.gestorempleo.application.domain.port.in.CreateInterviewCommand;
import com.gestorempleo.application.domain.port.in.InterviewUseCase;
import com.gestorempleo.application.domain.port.in.UpdateInterviewCommand;
import com.gestorempleo.application.domain.port.out.CompanyRepositoryPort;
import com.gestorempleo.application.domain.port.out.InterviewRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class InterviewService implements InterviewUseCase {

    private final InterviewRepositoryPort interviewRepositoryPort;
    private final CompanyRepositoryPort companyRepositoryPort;

    public InterviewService(InterviewRepositoryPort interviewRepositoryPort, CompanyRepositoryPort companyRepositoryPort) {
        this.interviewRepositoryPort = interviewRepositoryPort;
        this.companyRepositoryPort = companyRepositoryPort;
    }

    @Override
    @Transactional
    public Interview create(CreateInterviewCommand command) {
        validateInterviewRules(command.companyId(), command.type(), null);
        var interview = new Interview(
                null,
                command.companyId(),
                command.type(),
                command.interviewDate(),
                command.interviewTime(),
                command.status(),
                command.notes(),
                LocalDateTime.now()
        );
        return interviewRepositoryPort.save(interview);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Interview> findAll() {
        return interviewRepositoryPort.findAll()
                .stream()
                .sorted(Comparator.comparing(Interview::interviewDate).thenComparing(Interview::interviewTime))
                .toList();
    }

    @Override
    @Transactional
    public Interview update(UpdateInterviewCommand command) {
        var currentInterview = interviewRepositoryPort.findById(command.id())
                .orElseThrow(() -> new IllegalArgumentException("Interview not found"));
        validateInterviewRules(command.companyId(), command.type(), command.id());
        var interview = new Interview(
                currentInterview.id(),
                command.companyId(),
                command.type(),
                command.interviewDate(),
                command.interviewTime(),
                command.status(),
                command.notes(),
                currentInterview.createdAt()
        );
        return interviewRepositoryPort.save(interview);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        interviewRepositoryPort.deleteById(id);
    }

    private void validateInterviewRules(Long companyId, String type, Long currentInterviewId) {
        var company = companyRepositoryPort.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Offer not found"));

        var duplicated = currentInterviewId == null
                ? interviewRepositoryPort.existsByCompanyIdAndType(companyId, type)
                : interviewRepositoryPort.existsByCompanyIdAndTypeAndIdNot(companyId, type, currentInterviewId);

        if (duplicated) {
            throw new IllegalStateException("This offer already has an interview of this type");
        }

        var currentStage = getStageIndex(company.recruiterProcessNotes(), company.consultancyProcessNotes(), company.finalClientProcessNotes());
        var requestedStage = getTypeIndex(type);

        if (requestedStage < currentStage) {
            throw new IllegalStateException("Cannot create an interview for a previous process stage");
        }
    }

    private int getStageIndex(String recruiterNotes, String consultancyNotes, String finalClientNotes) {
        if (hasText(finalClientNotes)) {
            return 2;
        }

        if (hasText(consultancyNotes)) {
            return 1;
        }

        return 0;
    }

    private int getTypeIndex(String type) {
        return switch (type) {
            case "CLIENTE_FINAL" -> 2;
            case "CONSULTORA" -> 1;
            default -> 0;
        };
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}
