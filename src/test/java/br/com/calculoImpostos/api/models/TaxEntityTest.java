package br.com.calculoImpostos.api.models;

import br.com.calculoImpostos.api.enums.TypeTax;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class TaxEntityTest {
    @Test
    void testConstructorAndGetters() {
        // Arrange
        Long expectedId = 1L;
        TypeTax expectedName = TypeTax.ICMS;
        String expectedDescription = "Imposto sobre Circulação de Mercadorias e Serviços";
        Double expectedAliquot = 18.0;

        // Act
        TaxEntity taxEntity = new TaxEntity(expectedId, expectedName, expectedDescription, expectedAliquot);

        // Assert
        Assertions.assertEquals(expectedId, taxEntity.getId(), "O ID deve ser igual ao esperado.");
        Assertions.assertEquals(expectedName, taxEntity.getName(), "O nome do imposto deve ser igual ao esperado.");
        Assertions.assertEquals(expectedDescription, taxEntity.getDescription(), "A descrição deve ser igual ao esperado.");
        Assertions.assertEquals(expectedAliquot, taxEntity.getAliquot(), "A alíquota deve ser igual ao esperado.");
    }

    @Test
    void testSetters() {
        // Arrange
        TaxEntity taxEntity = new TaxEntity();
        Long expectedId = 1L;
        TypeTax expectedName = TypeTax.ISS;
        String expectedDescription = "Imposto sobre Serviços";
        Double expectedAliquot = 5.0;

        // Act
        taxEntity.setId(expectedId);
        taxEntity.setName(expectedName);
        taxEntity.setDescription(expectedDescription);
        taxEntity.setAliquot(expectedAliquot);

        // Assert
        Assertions.assertEquals(expectedId, taxEntity.getId(), "O ID deve ser igual ao esperado.");
        Assertions.assertEquals(expectedName, taxEntity.getName(), "O nome do imposto deve ser igual ao esperado.");
        Assertions.assertEquals(expectedDescription, taxEntity.getDescription(), "A descrição deve ser igual ao esperado.");
        Assertions.assertEquals(expectedAliquot, taxEntity.getAliquot(), "A alíquota deve ser igual ao esperado.");
    }
}