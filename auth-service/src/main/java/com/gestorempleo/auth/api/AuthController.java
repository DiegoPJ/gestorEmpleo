package com.gestorempleo.auth.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return new AuthResponse("pending-jwt", request.email());
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return new AuthResponse("pending-jwt", request.email());
    }

    @GetMapping("/validate")
    public TokenValidationResponse validate() {
        return new TokenValidationResponse(true);
    }

    public record RegisterRequest(String email, String password, String fullName) {
    }

    public record LoginRequest(String email, String password) {
    }

    public record AuthResponse(String token, String email) {
    }

    public record TokenValidationResponse(boolean valid) {
    }
}
