package br.com.calculoImpostos.api.models;

import br.com.calculoImpostos.api.enums.TypeTax;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TaxCalculationEntityTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        Long expectedId = 1L;
        TaxEntity expectedTax = new TaxEntity(1L, TypeTax.ICMS, "Descrição", 10.0);
        double expectedBaseValue = 100.0;
        double expectedValueCalculated = 10.0;

        // Act
        TaxCalculationEntity entity = new TaxCalculationEntity(expectedId, expectedTax, expectedBaseValue, expectedValueCalculated);

        // Assert
        Assertions.assertEquals(expectedId, entity.getId(), "O ID deve ser igual ao esperado.");
        Assertions.assertEquals(expectedTax, entity.getTax(), "O imposto deve ser igual ao esperado.");
        Assertions.assertEquals(expectedBaseValue, entity.getBaseValue(), "O valor base deve ser igual ao esperado.");
        Assertions.assertEquals(expectedValueCalculated, entity.getValueCalculated(), "O valor calculado deve ser igual ao esperado.");
    }

    @Test
    void testSetters() {
        // Arrange
        TaxCalculationEntity entity = new TaxCalculationEntity();
        Long expectedId = 1L;
        TaxEntity expectedTax = new TaxEntity(1L, TypeTax.ICMS, "Descrição", 10.0);
        double expectedBaseValue = 100.0;
        double expectedValueCalculated = 10.0;

        // Act
        entity.setId(expectedId);
        entity.setTax(expectedTax);
        entity.setBaseValue(expectedBaseValue);
        entity.setValueCalculated(expectedValueCalculated);

        // Assert
        Assertions.assertEquals(expectedId, entity.getId(), "O ID deve ser igual ao esperado.");
        Assertions.assertEquals(expectedTax, entity.getTax(), "O imposto deve ser igual ao esperado.");
        Assertions.assertEquals(expectedBaseValue, entity.getBaseValue(), "O valor base deve ser igual ao esperado.");
        Assertions.assertEquals(expectedValueCalculated, entity.getValueCalculated(), "O valor calculado deve ser igual ao esperado.");
    }
}