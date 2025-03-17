package br.com.calculoImpostos.api.dtos;

import jakarta.validation.constraints.Positive;

public record TaxCalculationRequestDTO(

        @Positive(message = "O ID do tipo de imposto tem que ser maior que zero")
        Long typeTaxId,
        @Positive(message = "O valorBase tem que ser maior que zero")
        double baseValue
) {

}
