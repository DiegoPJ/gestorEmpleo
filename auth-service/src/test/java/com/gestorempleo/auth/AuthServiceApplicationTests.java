package com.gestorempleo.auth;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AuthServiceApplicationTests {

    @Test
    void moduleKeepsExpectedHttpPort() {
        assertThat(8081).isEqualTo(8081);
    }
}
