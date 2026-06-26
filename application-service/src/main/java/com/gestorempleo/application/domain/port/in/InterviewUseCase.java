package com.gestorempleo.application.domain.port.in;

import com.gestorempleo.application.domain.model.Interview;

import java.util.List;

public interface InterviewUseCase {

    Interview create(CreateInterviewCommand command);

    List<Interview> findAll();

    Interview update(UpdateInterviewCommand command);

    void deleteById(Long id);
}
