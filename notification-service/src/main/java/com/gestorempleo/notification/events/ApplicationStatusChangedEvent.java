package com.gestorempleo.notification.events;

import java.time.LocalDate;

public record ApplicationStatusChangedEvent(
        Long applicationId,
        String previousStatus,
        String newStatus,
        LocalDate eventDate
) {
}
