package com.demo.mockapi.controller;

import com.demo.mockapi.dto.MockRequest;
import com.demo.mockapi.dto.MockResponse;
import com.demo.mockapi.exception.MockServerErrorException;
import com.demo.mockapi.exception.MockUnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/api/mock")
public class MockController {

    private static final Logger log = LoggerFactory.getLogger(MockController.class);

    private final Random random = new Random();

    @PostMapping("/submit")
    public ResponseEntity<MockResponse> submit(
            @RequestBody MockRequest request,
            @AuthenticationPrincipal Jwt jwt) {

        int roll = random.nextInt(100);
        String clientId = jwt.getSubject();

        log.info("Mock request from '{}' | tx={} | roll={}", clientId, request.getTransactionId(), roll);

        if (roll < 5) {
            // 5% chance -> 401 Unauthorized
            log.warn("Throwing 401 for tx={}", request.getTransactionId());
            throw new MockUnauthorizedException(
                "Simulated 401 Unauthorized — token rejected for tx=" + request.getTransactionId()
            );

        } else if (roll < 10) {
            // 5% chance -> 500 Internal Server Error
            log.warn("Throwing 500 for tx={}", request.getTransactionId());
            throw new MockServerErrorException(
                "Simulated 500 Internal Server Error — processing failure for tx=" + request.getTransactionId()
            );
        }

        // 90% chance -> 200 OK
        MockResponse response = new MockResponse(
            "success",
            request.getTransactionId(),
            request.getPayload(),
            "Request processed successfully"
        );

        return ResponseEntity.ok(response);
    }

}
