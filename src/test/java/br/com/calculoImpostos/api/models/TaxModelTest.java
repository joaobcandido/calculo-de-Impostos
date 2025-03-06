package br.com.calculoImpostos.api.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class TaxModelTest {
    @Test
    void testCreateTaxModelWithDefaultConstructor() {
        // Arrange & Act
        TaxModel taxModel = new TaxModel();

        // Assert
        Assertions.assertNotNull(taxModel, " TaxModel não deve ser nulo.");
    }

}