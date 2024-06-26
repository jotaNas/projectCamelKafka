package com.nttdata.dto.response;

import jakarta.json.bind.annotation.JsonbProperty;

public class ErrorResponse {

    @JsonbProperty("status_code")
    private int statusCode;

    @JsonbProperty("message")
    private String message;

    @JsonbProperty("timestamp")
    private String timestamp;

    public ErrorResponse() {}

    public ErrorResponse(int statusCode, String message, String timestamp) {
        this.statusCode = statusCode;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

