package com.gestorempleo.application.infrastructure.adapter.in.web;

import com.gestorempleo.application.domain.model.LinkedinExplanation;
import com.gestorempleo.application.domain.port.in.CreateLinkedinExplanationCommand;
import com.gestorempleo.application.domain.port.in.LinkedinExplanationUseCase;
import com.gestorempleo.application.domain.port.in.UpdateLinkedinExplanationCommand;
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
@RequestMapping("/api/linkedin-explanations")
public class LinkedinExplanationController {

    private final LinkedinExplanationUseCase linkedinExplanationUseCase;

    public LinkedinExplanationController(LinkedinExplanationUseCase linkedinExplanationUseCase) {
        this.linkedinExplanationUseCase = linkedinExplanationUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LinkedinExplanationResponse create(@RequestBody LinkedinExplanationRequest request) {
        return LinkedinExplanationResponse.from(linkedinExplanationUseCase.create(
                new CreateLinkedinExplanationCommand(request.question(), request.answer())
        ));
    }

    @GetMapping
    public List<LinkedinExplanationResponse> findAll() {
        return linkedinExplanationUseCase.findAll()
                .stream()
                .map(LinkedinExplanationResponse::from)
                .toList();
    }

    @PutMapping("/{id}")
    public LinkedinExplanationResponse update(@PathVariable("id") Long id, @RequestBody LinkedinExplanationRequest request) {
        return LinkedinExplanationResponse.from(linkedinExplanationUseCase.update(
                new UpdateLinkedinExplanationCommand(id, request.question(), request.answer())
        ));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id) {
        linkedinExplanationUseCase.deleteById(id);
    }

    public record LinkedinExplanationRequest(String question, String answer) {
    }

    public record LinkedinExplanationResponse(Long id, String question, String answer, LocalDateTime createdAt) {

        static LinkedinExplanationResponse from(LinkedinExplanation explanation) {
            return new LinkedinExplanationResponse(
                    explanation.id(),
                    explanation.question(),
                    explanation.answer(),
                    explanation.createdAt()
            );
        }
    }
}
