package com.gestorempleo.application.application.service;

import com.gestorempleo.application.domain.model.TargetCompany;
import com.gestorempleo.application.domain.port.in.CreateTargetCompanyCommand;
import com.gestorempleo.application.domain.port.in.TargetCompanyUseCase;
import com.gestorempleo.application.domain.port.in.UpdateTargetCompanyCommand;
import com.gestorempleo.application.domain.port.out.TargetCompanyRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TargetCompanyService implements TargetCompanyUseCase {

    private final TargetCompanyRepositoryPort targetCompanyRepositoryPort;

    public TargetCompanyService(TargetCompanyRepositoryPort targetCompanyRepositoryPort) {
        this.targetCompanyRepositoryPort = targetCompanyRepositoryPort;
    }

    @Override
    @Transactional
    public TargetCompany create(CreateTargetCompanyCommand command) {
        var company = new TargetCompany(
                null,
                command.name(),
                false,
                false,
                false,
                false,
                false,
                LocalDateTime.now()
        );
        return targetCompanyRepositoryPort.save(company);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TargetCompany> findAll() {
        return targetCompanyRepositoryPort.findAll();
    }

    @Override
    @Transactional
    public TargetCompany update(UpdateTargetCompanyCommand command) {
        var currentCompany = targetCompanyRepositoryPort.findById(command.id())
                .orElseThrow(() -> new IllegalArgumentException("Target company not found"));
        var company = new TargetCompany(
                currentCompany.id(),
                command.name(),
                command.recruiterContacted(),
                command.companyMemberContacted(),
                command.cvSent(),
                command.replied(),
                command.waiting(),
                currentCompany.createdAt()
        );
        return targetCompanyRepositoryPort.save(company);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        targetCompanyRepositoryPort.deleteById(id);
    }
}
