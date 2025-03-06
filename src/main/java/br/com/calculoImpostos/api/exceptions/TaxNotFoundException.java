package br.com.calculoImpostos.api.exceptions;

public class TaxNotFoundException extends RuntimeException {

    public TaxNotFoundException(String message) {
        super(message);
    }
}
