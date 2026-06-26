package com.gestorempleo.application.infrastructure.adapter.in.web;

import com.gestorempleo.application.domain.model.Company;
import com.gestorempleo.application.domain.port.in.CreateCompanyCommand;
import com.gestorempleo.application.domain.port.in.CreateCompanyUseCase;
import com.gestorempleo.application.domain.port.in.DeleteCompanyUseCase;
import com.gestorempleo.application.domain.port.in.FindCompaniesUseCase;
import com.gestorempleo.application.domain.port.in.UpdateCompanyCommand;
import com.gestorempleo.application.domain.port.in.UpdateCompanyUseCase;
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
@RequestMapping("/api/companies")
public class CompanyController {

    private final CreateCompanyUseCase createCompanyUseCase;
    private final FindCompaniesUseCase findCompaniesUseCase;
    private final DeleteCompanyUseCase deleteCompanyUseCase;
    private final UpdateCompanyUseCase updateCompanyUseCase;

    public CompanyController(
            CreateCompanyUseCase createCompanyUseCase,
            FindCompaniesUseCase findCompaniesUseCase,
            DeleteCompanyUseCase deleteCompanyUseCase,
            UpdateCompanyUseCase updateCompanyUseCase
    ) {
        this.createCompanyUseCase = createCompanyUseCase;
        this.findCompaniesUseCase = findCompaniesUseCase;
        this.deleteCompanyUseCase = deleteCompanyUseCase;
        this.updateCompanyUseCase = updateCompanyUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyResponse create(@RequestBody CreateCompanyRequest request) {
        var company = createCompanyUseCase.create(new CreateCompanyCommand(
                request.name(),
                request.offerTitle(),
                request.contactName(),
                request.contactType(),
                request.offerComment(),
                request.recruiterProcessNotes(),
                request.consultancyProcessNotes(),
                request.finalClientProcessNotes()
        ));

        return CompanyResponse.from(company);
    }

    @GetMapping
    public List<CompanyResponse> findAll() {
        return findCompaniesUseCase.findAll()
                .stream()
                .map(CompanyResponse::from)
                .toList();
    }

    @PutMapping("/{id}")
    public CompanyResponse update(@PathVariable("id") Long id, @RequestBody UpdateCompanyRequest request) {
        var company = updateCompanyUseCase.update(new UpdateCompanyCommand(
                id,
                request.name(),
                request.offerTitle(),
                request.contactName(),
                request.contactType(),
                request.offerComment(),
                request.recruiterProcessNotes(),
                request.consultancyProcessNotes(),
                request.finalClientProcessNotes()
        ));

        return CompanyResponse.from(company);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id) {
        deleteCompanyUseCase.deleteById(id);
    }

    public record CreateCompanyRequest(
            String name,
            String offerTitle,
            String contactName,
            String contactType,
            String offerComment,
            String recruiterProcessNotes,
            String consultancyProcessNotes,
            String finalClientProcessNotes
    ) {
    }

    public record UpdateCompanyRequest(
            String name,
            String offerTitle,
            String contactName,
            String contactType,
            String offerComment,
            String recruiterProcessNotes,
            String consultancyProcessNotes,
            String finalClientProcessNotes
    ) {
    }

    public record CompanyResponse(
            Long id,
            String name,
            String offerTitle,
            String contactName,
            String contactType,
            String offerComment,
            String recruiterProcessNotes,
            String consultancyProcessNotes,
            String finalClientProcessNotes,
            LocalDateTime createdAt
    ) {

        static CompanyResponse from(Company company) {
            var contactType = company.contactType() == null ? null : company.contactType().name();
            return new CompanyResponse(
                    company.id(),
                    company.name(),
                    company.offerTitle(),
                    company.contactName(),
                    contactType,
                    company.offerComment(),
                    company.recruiterProcessNotes(),
                    company.consultancyProcessNotes(),
                    company.finalClientProcessNotes(),
                    company.createdAt()
            );
        }
    }
}
