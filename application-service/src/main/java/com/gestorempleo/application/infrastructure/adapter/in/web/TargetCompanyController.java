package com.gestorempleo.application.infrastructure.adapter.in.web;

import com.gestorempleo.application.domain.model.TargetCompany;
import com.gestorempleo.application.domain.port.in.CreateTargetCompanyCommand;
import com.gestorempleo.application.domain.port.in.TargetCompanyUseCase;
import com.gestorempleo.application.domain.port.in.UpdateTargetCompanyCommand;
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
@RequestMapping("/api/target-companies")
public class TargetCompanyController {

    private final TargetCompanyUseCase targetCompanyUseCase;

    public TargetCompanyController(TargetCompanyUseCase targetCompanyUseCase) {
        this.targetCompanyUseCase = targetCompanyUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TargetCompanyResponse create(@RequestBody CreateTargetCompanyRequest request) {
        return TargetCompanyResponse.from(targetCompanyUseCase.create(new CreateTargetCompanyCommand(request.name())));
    }

    @GetMapping
    public List<TargetCompanyResponse> findAll() {
        return targetCompanyUseCase.findAll()
                .stream()
                .map(TargetCompanyResponse::from)
                .toList();
    }

    @PutMapping("/{id}")
    public TargetCompanyResponse update(@PathVariable("id") Long id, @RequestBody UpdateTargetCompanyRequest request) {
        var company = targetCompanyUseCase.update(new UpdateTargetCompanyCommand(
                id,
                request.name(),
                request.recruiterContacted(),
                request.companyMemberContacted(),
                request.cvSent(),
                request.replied(),
                request.waiting()
        ));
        return TargetCompanyResponse.from(company);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id) {
        targetCompanyUseCase.deleteById(id);
    }

    public record CreateTargetCompanyRequest(String name) {
    }

    public record UpdateTargetCompanyRequest(
            String name,
            boolean recruiterContacted,
            boolean companyMemberContacted,
            boolean cvSent,
            boolean replied,
            boolean waiting
    ) {
    }

    public record TargetCompanyResponse(
            Long id,
            String name,
            boolean recruiterContacted,
            boolean companyMemberContacted,
            boolean cvSent,
            boolean replied,
            boolean waiting,
            LocalDateTime createdAt
    ) {
        static TargetCompanyResponse from(TargetCompany company) {
            return new TargetCompanyResponse(
                    company.id(),
                    company.name(),
                    company.recruiterContacted(),
                    company.companyMemberContacted(),
                    company.cvSent(),
                    company.replied(),
                    company.waiting(),
                    company.createdAt()
            );
        }
    }
}
