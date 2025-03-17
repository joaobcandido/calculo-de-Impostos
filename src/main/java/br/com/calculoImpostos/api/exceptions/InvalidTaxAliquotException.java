package br.com.calculoImpostos.api.exceptions;

public class InvalidTaxAliquotException extends RuntimeException {
    public InvalidTaxAliquotException(String message) {
        super(message);
    }
}
