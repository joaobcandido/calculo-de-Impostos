package br.com.calculoImpostos.api.dtos;

public record TaxCalculationResponseDTO(
        String typeTax,
        double baseValue,
        double aliquot,
        double valueTax
) {

}