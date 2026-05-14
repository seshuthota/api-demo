package com.demo.mockapi.dto;

import java.util.Map;

public class MockResponse {

    private String status;
    private String transactionId;
    private Map<String, Object> payload;
    private String message;

    public MockResponse() {}

    public MockResponse(String status, String transactionId, Map<String, Object> payload, String message) {
        this.status = status;
        this.transactionId = transactionId;
        this.payload = payload;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
