package com.demo.mockapi.controller;

import com.demo.mockapi.dto.AuthRequest;
import com.demo.mockapi.dto.AuthResponse;
import com.demo.mockapi.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/token")
    public ResponseEntity<AuthResponse> generateToken(@RequestBody AuthRequest request) {
        if (request.getClientId() == null || request.getClientId().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        log.info("Token requested for client: {}", request.getClientId());

        String token = jwtService.generateToken(request.getClientId());
        AuthResponse response = new AuthResponse(token, "Bearer", 3600L);

        return ResponseEntity.ok(response);
    }

}
