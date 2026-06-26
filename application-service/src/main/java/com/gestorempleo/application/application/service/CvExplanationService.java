package com.gestorempleo.application.application.service;

import com.gestorempleo.application.domain.model.CvExplanation;
import com.gestorempleo.application.domain.port.in.CreateCvExplanationCommand;
import com.gestorempleo.application.domain.port.in.CvExplanationUseCase;
import com.gestorempleo.application.domain.port.in.UpdateCvExplanationCommand;
import com.gestorempleo.application.domain.port.out.CvExplanationRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CvExplanationService implements CvExplanationUseCase {

    private final CvExplanationRepositoryPort cvExplanationRepositoryPort;

    public CvExplanationService(CvExplanationRepositoryPort cvExplanationRepositoryPort) {
        this.cvExplanationRepositoryPort = cvExplanationRepositoryPort;
    }

    @Override
    @Transactional
    public CvExplanation create(CreateCvExplanationCommand command) {
        var explanation = new CvExplanation(null, command.question(), command.answer(), LocalDateTime.now());
        return cvExplanationRepositoryPort.save(explanation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CvExplanation> findAll() {
        return cvExplanationRepositoryPort.findAll();
    }

    @Override
    @Transactional
    public CvExplanation update(UpdateCvExplanationCommand command) {
        var currentExplanation = cvExplanationRepositoryPort.findById(command.id())
                .orElseThrow(() -> new IllegalArgumentException("CV explanation not found"));
        var explanation = new CvExplanation(
                currentExplanation.id(),
                command.question(),
                command.answer(),
                currentExplanation.createdAt()
        );
        return cvExplanationRepositoryPort.save(explanation);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        cvExplanationRepositoryPort.deleteById(id);
    }
}
