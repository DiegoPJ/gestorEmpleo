package com.gestorempleo.application.infrastructure.adapter.out.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "target_companies")
public class TargetCompanyJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private boolean recruiterContacted;

    private boolean companyMemberContacted;

    private boolean cvSent;

    private boolean replied;

    private boolean waiting;

    private LocalDateTime createdAt;

    protected TargetCompanyJpaEntity() {
    }

    public TargetCompanyJpaEntity(
            Long id,
            String name,
            boolean recruiterContacted,
            boolean companyMemberContacted,
            boolean cvSent,
            boolean replied,
            boolean waiting,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.name = name;
        this.recruiterContacted = recruiterContacted;
        this.companyMemberContacted = companyMemberContacted;
        this.cvSent = cvSent;
        this.replied = replied;
        this.waiting = waiting;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isRecruiterContacted() {
        return recruiterContacted;
    }

    public boolean isCompanyMemberContacted() {
        return companyMemberContacted;
    }

    public boolean isCvSent() {
        return cvSent;
    }

    public boolean isReplied() {
        return replied;
    }

    public boolean isWaiting() {
        return waiting;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
