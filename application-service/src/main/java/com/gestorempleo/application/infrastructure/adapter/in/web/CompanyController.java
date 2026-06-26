package com.gestorempleo.application.infrastructure.adapter.in.web;

import com.gestorempleo.application.domain.model.Company;
import com.gestorempleo.application.domain.port.in.CreateCompanyCommand;
import com.gestorempleo.application.domain.port.in.CreateCompanyUseCase;
import com.gestorempleo.application.domain.port.in.FindCompaniesUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CreateCompanyUseCase createCompanyUseCase;
    private final FindCompaniesUseCase findCompaniesUseCase;

    public CompanyController(CreateCompanyUseCase createCompanyUseCase, FindCompaniesUseCase findCompaniesUseCase) {
        this.createCompanyUseCase = createCompanyUseCase;
        this.findCompaniesUseCase = findCompaniesUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyResponse create(@RequestBody CreateCompanyRequest request) {
        var company = createCompanyUseCase.create(new CreateCompanyCommand(
                request.name(),
                request.sector(),
                request.website()
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

    public record CreateCompanyRequest(String name, String sector, String website) {
    }

    public record CompanyResponse(Long id, String name, String sector, String website) {

        static CompanyResponse from(Company company) {
            return new CompanyResponse(company.id(), company.name(), company.sector(), company.website());
        }
    }
}
