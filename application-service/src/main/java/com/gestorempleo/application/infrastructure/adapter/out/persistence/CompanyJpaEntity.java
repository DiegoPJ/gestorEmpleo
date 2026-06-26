package com.gestorempleo.application.infrastructure.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "companies")
public class CompanyJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String sector;

    private String website;

    protected CompanyJpaEntity() {
    }

    public CompanyJpaEntity(Long id, String name, String sector, String website) {
        this.id = id;
        this.name = name;
        this.sector = sector;
        this.website = website;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSector() {
        return sector;
    }

    public String getWebsite() {
        return website;
    }
}
