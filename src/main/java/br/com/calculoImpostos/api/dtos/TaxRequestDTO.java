package br.com.calculoImpostos.api.dtos;


import br.com.calculoImpostos.api.enums.TypeTax;

public record TaxRequestDTO(
        TypeTax name,
        String description,
        double aliquot
) {

}

