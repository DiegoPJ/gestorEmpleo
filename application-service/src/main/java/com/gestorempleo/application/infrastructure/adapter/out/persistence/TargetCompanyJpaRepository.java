package com.gestorempleo.application.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface TargetCompanyJpaRepository extends JpaRepository<TargetCompanyJpaEntity, Long> {
}
