package com.gestorempleo.application.infrastructure.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "companies")
public class CompanyJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 180)
    private String offerTitle;

    private String contactName;

    private String contactType;

    @Column(columnDefinition = "TEXT")
    private String offerComment;

    @Column(columnDefinition = "TEXT")
    private String recruiterProcessNotes;

    @Column(columnDefinition = "TEXT")
    private String consultancyProcessNotes;

    @Column(columnDefinition = "TEXT")
    private String finalClientProcessNotes;

    private LocalDateTime createdAt;

    protected CompanyJpaEntity() {
    }

    public CompanyJpaEntity(
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
        this.id = id;
        this.name = name;
        this.offerTitle = offerTitle;
        this.contactName = contactName;
        this.contactType = contactType;
        this.offerComment = offerComment;
        this.recruiterProcessNotes = recruiterProcessNotes;
        this.consultancyProcessNotes = consultancyProcessNotes;
        this.finalClientProcessNotes = finalClientProcessNotes;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOfferTitle() {
        return offerTitle;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactType() {
        return contactType;
    }

    public String getOfferComment() {
        return offerComment;
    }

    public String getRecruiterProcessNotes() {
        return recruiterProcessNotes;
    }

    public String getConsultancyProcessNotes() {
        return consultancyProcessNotes;
    }

    public String getFinalClientProcessNotes() {
        return finalClientProcessNotes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
