package br.com.calculoImpostos.api.services;

import br.com.calculoImpostos.api.enums.TypeTax;
import br.com.calculoImpostos.api.exceptions.TaxAlreadyRegisteredException;
import br.com.calculoImpostos.api.models.TaxEntity;
import br.com.calculoImpostos.api.repositories.TaxRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TaxValidationServiceTest {

    @Mock
    private TaxRepository taxRepository;

    @InjectMocks
    private TaxValidationService taxValidationService;

    @Test
    void testValidateTaxNameThrowsExceptionWhenNameExists() {
        // Arrange
        TypeTax name = TypeTax.ICMS;
        TaxEntity existingTax = new TaxEntity(1L, name, "Descrição", 18.0);
        Mockito.when(taxRepository.findByName(name)).thenReturn(Optional.of(existingTax));

        // Act & Assert
        Assertions.assertThrows(TaxAlreadyRegisteredException.class,
                () -> taxValidationService.validateTaxName(name),
                "Deveria lançar TaxAlreadyRegisteredException quando o nome já existe.");
    }

    @Test
    void testValidateTaxNameDoesNotThrowExceptionWhenNameDoesNotExist() {
        // Arrange
        TypeTax name = TypeTax.ICMS;
        Mockito.when(taxRepository.findByName(name)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertDoesNotThrow(() -> taxValidationService.validateTaxName(name),
                "Não deveria lançar exceção quando o nome não existe.");
    }
}