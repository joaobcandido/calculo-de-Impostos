package br.com.calculoImpostos.api.exceptions;

public class TaxAlreadyRegisteredException extends RuntimeException {

    public TaxAlreadyRegisteredException(String message) {
        super(message);
    }
}
