package br.com.calculoImpostos.api.dtos;


import br.com.calculoImpostos.api.enums.TypeTax;

public record TaxResponseDTO(
        Long id,
        TypeTax name,
        String description,
        double aliquot
) {
}
