package br.com.calculoImpostos.api.exceptions;

import java.util.List;

public class ErrorResponse {
    private int statusCode;
    private List<String> messages;

    public ErrorResponse(int statusCode, List<String> messages) {
        this.statusCode = statusCode;
        this.messages = messages;
    }

    public List<String> getMessages() {
        return messages;
    }

    public int getStatusCode() {
        return statusCode;
    }
}

