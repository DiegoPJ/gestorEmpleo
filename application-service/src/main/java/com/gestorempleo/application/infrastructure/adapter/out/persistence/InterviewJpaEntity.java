package com.gestorempleo.application.infrastructure.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "interviews")
public class InterviewJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long companyId;

    private String type;

    private LocalDate interviewDate;

    private LocalTime interviewTime;

    private String status;

    @Column(columnDefinition = "TEXT")
    private String notes;

    private LocalDateTime createdAt;

    protected InterviewJpaEntity() {
    }

    public InterviewJpaEntity(
            Long id,
            Long companyId,
            String type,
            LocalDate interviewDate,
            LocalTime interviewTime,
            String status,
            String notes,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.companyId = companyId;
        this.type = type;
        this.interviewDate = interviewDate;
        this.interviewTime = interviewTime;
        this.status = status;
        this.notes = notes;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public String getType() {
        return type;
    }

    public LocalDate getInterviewDate() {
        return interviewDate;
    }

    public LocalTime getInterviewTime() {
        return interviewTime;
    }

    public String getStatus() {
        return status;
    }

    public String getNotes() {
        return notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
