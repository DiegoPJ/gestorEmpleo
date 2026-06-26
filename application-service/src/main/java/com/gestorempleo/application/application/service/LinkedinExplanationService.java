package com.gestorempleo.application.application.service;

import com.gestorempleo.application.domain.model.LinkedinExplanation;
import com.gestorempleo.application.domain.port.in.CreateLinkedinExplanationCommand;
import com.gestorempleo.application.domain.port.in.LinkedinExplanationUseCase;
import com.gestorempleo.application.domain.port.in.UpdateLinkedinExplanationCommand;
import com.gestorempleo.application.domain.port.out.LinkedinExplanationRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LinkedinExplanationService implements LinkedinExplanationUseCase {

    private final LinkedinExplanationRepositoryPort linkedinExplanationRepositoryPort;

    public LinkedinExplanationService(LinkedinExplanationRepositoryPort linkedinExplanationRepositoryPort) {
        this.linkedinExplanationRepositoryPort = linkedinExplanationRepositoryPort;
    }

    @Override
    @Transactional
    public LinkedinExplanation create(CreateLinkedinExplanationCommand command) {
        var explanation = new LinkedinExplanation(null, command.question(), command.answer(), LocalDateTime.now());
        return linkedinExplanationRepositoryPort.save(explanation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LinkedinExplanation> findAll() {
        return linkedinExplanationRepositoryPort.findAll();
    }

    @Override
    @Transactional
    public LinkedinExplanation update(UpdateLinkedinExplanationCommand command) {
        var currentExplanation = linkedinExplanationRepositoryPort.findById(command.id())
                .orElseThrow(() -> new IllegalArgumentException("LinkedIn explanation not found"));
        var explanation = new LinkedinExplanation(
                currentExplanation.id(),
                command.question(),
                command.answer(),
                currentExplanation.createdAt()
        );
        return linkedinExplanationRepositoryPort.save(explanation);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        linkedinExplanationRepositoryPort.deleteById(id);
    }
}
