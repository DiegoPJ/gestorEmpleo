package com.gestorempleo.application.infrastructure.adapter.in.web;

import com.gestorempleo.application.domain.model.CvExplanation;
import com.gestorempleo.application.domain.port.in.CreateCvExplanationCommand;
import com.gestorempleo.application.domain.port.in.CvExplanationUseCase;
import com.gestorempleo.application.domain.port.in.UpdateCvExplanationCommand;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/cv-explanations")
public class CvExplanationController {

    private final CvExplanationUseCase cvExplanationUseCase;

    public CvExplanationController(CvExplanationUseCase cvExplanationUseCase) {
        this.cvExplanationUseCase = cvExplanationUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CvExplanationResponse create(@RequestBody CvExplanationRequest request) {
        return CvExplanationResponse.from(cvExplanationUseCase.create(
                new CreateCvExplanationCommand(request.question(), request.answer())
        ));
    }

    @GetMapping
    public List<CvExplanationResponse> findAll() {
        return cvExplanationUseCase.findAll()
                .stream()
                .map(CvExplanationResponse::from)
                .toList();
    }

    @PutMapping("/{id}")
    public CvExplanationResponse update(@PathVariable("id") Long id, @RequestBody CvExplanationRequest request) {
        return CvExplanationResponse.from(cvExplanationUseCase.update(
                new UpdateCvExplanationCommand(id, request.question(), request.answer())
        ));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id) {
        cvExplanationUseCase.deleteById(id);
    }

    public record CvExplanationRequest(String question, String answer) {
    }

    public record CvExplanationResponse(Long id, String question, String answer, LocalDateTime createdAt) {

        static CvExplanationResponse from(CvExplanation explanation) {
            return new CvExplanationResponse(
                    explanation.id(),
                    explanation.question(),
                    explanation.answer(),
                    explanation.createdAt()
            );
        }
    }
}
