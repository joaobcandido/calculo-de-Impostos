package br.com.calculoImpostos.api.mappers;

import br.com.calculoImpostos.api.dtos.TaxRequestDTO;
import br.com.calculoImpostos.api.dtos.TaxResponseDTO;
import br.com.calculoImpostos.api.enums.TypeTax;
import br.com.calculoImpostos.api.models.TaxEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaxMapperTest {
    private final TaxMapper taxMapper = new TaxMapper();

    @Test
    void testToEntity() {
        // Arrange
        TaxRequestDTO request = new TaxRequestDTO(TypeTax.ICMS, "Descrição", 18.0);

        // Act
        TaxEntity entity = taxMapper.toEntity(request);

        // Assert
        Assertions.assertNotNull(entity, "A entidade não deve ser nula.");
        Assertions.assertNull(entity.getId(), "O ID deve ser nulo para uma nova entidade.");
        Assertions.assertEquals(request.name(), entity.getName(), "O nome deve ser igual ao esperado.");
        Assertions.assertEquals(request.description(), entity.getDescription(), "A descrição deve ser igual ao esperado.");
        Assertions.assertEquals(request.aliquot(), entity.getAliquot(), "A alíquota deve ser igual ao esperado.");
    }

    @Test
    void testToResponseDTO() {
        // Arrange
        TaxEntity entity = new TaxEntity(1L, TypeTax.ICMS, "Descrição", 18.0);

        // Act
        TaxResponseDTO response = taxMapper.toResponseDTO(entity);

        // Assert
        Assertions.assertNotNull(response, "O DTO de resposta não deve ser nulo.");
        Assertions.assertEquals(entity.getId(), response.id(), "O ID deve ser igual ao esperado.");
        Assertions.assertEquals(entity.getName(), response.name(), "O nome deve ser igual ao esperado.");
        Assertions.assertEquals(entity.getDescription(), response.description(), "A descrição deve ser igual ao esperado.");
        Assertions.assertEquals(entity.getAliquot(), response.aliquot(), "A alíquota deve ser igual ao esperado.");
    }
}