package br.com.calculoImpostos.api.exceptions;

public class ErrorResponse {
    private int statusCode;
    private String message;

    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public String getMensagem() {
        return message;
    }


    public int getStatusCode() {
        return statusCode;
    }


}

