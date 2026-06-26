package com.gestorempleo.notification;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NotificationServiceApplicationTests {

    @Test
    void eventCarriesApplicationId() {
        assertThat("application.status.changed").startsWith("application");
    }
}
