package com.gestorempleo.application.infrastructure.adapter.in.web;

import com.gestorempleo.application.domain.model.Interview;
import com.gestorempleo.application.domain.port.in.CreateInterviewCommand;
import com.gestorempleo.application.domain.port.in.InterviewUseCase;
import com.gestorempleo.application.domain.port.in.UpdateInterviewCommand;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/interviews")
public class InterviewController {

    private final InterviewUseCase interviewUseCase;

    public InterviewController(InterviewUseCase interviewUseCase) {
        this.interviewUseCase = interviewUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InterviewResponse create(@RequestBody InterviewRequest request) {
        return InterviewResponse.from(interviewUseCase.create(new CreateInterviewCommand(
                request.companyId(),
                request.type(),
                request.interviewDate(),
                request.interviewTime(),
                request.status(),
                request.notes()
        )));
    }

    @GetMapping
    public List<InterviewResponse> findAll() {
        return interviewUseCase.findAll()
                .stream()
                .map(InterviewResponse::from)
                .toList();
    }

    @PutMapping("/{id}")
    public InterviewResponse update(@PathVariable("id") Long id, @RequestBody InterviewRequest request) {
        return InterviewResponse.from(interviewUseCase.update(new UpdateInterviewCommand(
                id,
                request.companyId(),
                request.type(),
                request.interviewDate(),
                request.interviewTime(),
                request.status(),
                request.notes()
        )));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id) {
        interviewUseCase.deleteById(id);
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidInterview(RuntimeException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    public record InterviewRequest(
            Long companyId,
            String type,
            LocalDate interviewDate,
            LocalTime interviewTime,
            String status,
            String notes
    ) {
    }

    public record ErrorResponse(String message) {
    }

    public record InterviewResponse(
            Long id,
            Long companyId,
            String type,
            LocalDate interviewDate,
            LocalTime interviewTime,
            String status,
            String notes,
            LocalDateTime createdAt
    ) {
        static InterviewResponse from(Interview interview) {
            return new InterviewResponse(
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
    }
}
