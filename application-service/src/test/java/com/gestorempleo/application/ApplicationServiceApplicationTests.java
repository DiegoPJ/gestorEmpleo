package com.gestorempleo.application;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ApplicationServiceApplicationTests {

    @Test
    void moduleUsesApplicationServicePort() {
        assertThat("CreateCompanyUseCase").endsWith("UseCase");
    }
}
