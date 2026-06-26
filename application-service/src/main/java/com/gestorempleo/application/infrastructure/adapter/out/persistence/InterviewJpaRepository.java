package com.gestorempleo.application.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface InterviewJpaRepository extends JpaRepository<InterviewJpaEntity, Long> {

    boolean existsByCompanyIdAndType(Long companyId, String type);

    boolean existsByCompanyIdAndTypeAndIdNot(Long companyId, String type, Long id);
}
