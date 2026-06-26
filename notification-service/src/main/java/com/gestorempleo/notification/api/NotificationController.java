package com.gestorempleo.notification.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @GetMapping
    public List<NotificationResponse> findAll() {
        return List.of(new NotificationResponse(
                "APPLICATION_STATUS_CHANGED",
                "La candidatura 15 ha cambiado a ENTREVISTA_TECNICA"
        ));
    }

    public record NotificationResponse(String type, String message) {
    }
}
