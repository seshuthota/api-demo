package com.demo.mockapi.dto;

import java.util.Map;

public class MockRequest {

    private String transactionId;
    private Map<String, Object> payload;

    public MockRequest() {}

    public MockRequest(String transactionId, Map<String, Object> payload) {
        this.transactionId = transactionId;
        this.payload = payload;
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

}
