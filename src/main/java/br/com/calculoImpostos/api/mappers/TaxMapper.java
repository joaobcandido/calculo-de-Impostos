package br.com.calculoImpostos.api.mappers;

import br.com.calculoImpostos.api.dtos.TaxRequestDTO;
import br.com.calculoImpostos.api.dtos.TaxResponseDTO;
import br.com.calculoImpostos.api.models.TaxEntity;
import org.springframework.stereotype.Component;

@Component
public class TaxMapper {

    public TaxEntity toEntity(TaxRequestDTO request) {
        return new TaxEntity(
                null,
                request.name(),
                request.description(),
                request.aliquot());
    }

    public TaxResponseDTO toResponseDTO(TaxEntity tax) {
        return new TaxResponseDTO(
                tax.getId(),
                tax.getName(),
                tax.getDescription(),
                tax.getAliquot());
    }
}
