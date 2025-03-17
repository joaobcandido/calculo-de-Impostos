package br.com.calculoImpostos.api.dtos;


import br.com.calculoImpostos.api.enums.TypeTax;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record TaxRequestDTO(

        TypeTax name,
        @NotBlank(message = "A descricao do imposto é obrigatoria")
        String description,
        @Positive(message = "A aliquota de calculo tem que ser maior que zero")
        double aliquot
) {

}

