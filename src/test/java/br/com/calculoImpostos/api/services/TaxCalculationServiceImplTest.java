package br.com.calculoImpostos.api.services;

import br.com.calculoImpostos.api.dtos.TaxCalculationRequestDTO;
import br.com.calculoImpostos.api.dtos.TaxCalculationResponseDTO;
import br.com.calculoImpostos.api.enums.TypeTax;
import br.com.calculoImpostos.api.exceptions.InvalidTaxAliquotException;
import br.com.calculoImpostos.api.exceptions.TaxNotFoundException;
import br.com.calculoImpostos.api.models.TaxCalculationEntity;
import br.com.calculoImpostos.api.models.TaxEntity;
import br.com.calculoImpostos.api.repositories.TaxCalculationRepository;
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
class TaxCalculationServiceImplTest {

    @Mock
    private TaxRepository taxRepository;

    @Mock
    private TaxCalculationRepository taxCalculationRepository;

    @InjectMocks
    private TaxCalculationServiceImpl taxCalculationService;

    @Test
    void testCalculateTaxSuccessfully() {
        // Arrange
        TaxCalculationRequestDTO request = new TaxCalculationRequestDTO(1L, 100.0);
        TaxEntity taxEntity = new TaxEntity(1L, TypeTax.ICMS, "Descrição", 10.0);

        Mockito.when(taxRepository.findById(request.typeTaxId())).thenReturn(Optional.of(taxEntity));

        // Act
        TaxCalculationResponseDTO response = taxCalculationService.calculateTax(request);

        // Assert
        Assertions.assertNotNull(response, "A resposta não deve ser nula.");
        Assertions.assertEquals("ICMS", response.typeTax(), "O tipo de imposto deve ser ICMS.");
        Assertions.assertEquals(100.0, response.baseValue(), "O valor base deve ser 100.0.");
        Assertions.assertEquals(10.0, response.aliquot(), "A alíquota deve ser 10.0.");
        Assertions.assertEquals(10.0, response.valueTax(), "O valor do imposto deve ser 10.0.");

        Mockito.verify(taxRepository, Mockito.times(1)).findById(request.typeTaxId());
        Mockito.verify(taxCalculationRepository, Mockito.times(1)).save(Mockito.any(TaxCalculationEntity.class));
    }
    @Test
    void testCalculateTaxWhenTaxNotFound() {
        // Arrange
        TaxCalculationRequestDTO request = new TaxCalculationRequestDTO(1L, 100.0);

        Mockito.when(taxRepository.findById(request.typeTaxId())).thenReturn(Optional.empty());

        // Act & Assert
        TaxNotFoundException exception = Assertions.assertThrows(
                TaxNotFoundException.class,
                () -> taxCalculationService.calculateTax(request),
                "Deveria lançar TaxNotFoundException"
        );

        Assertions.assertEquals("Imposto nao encontrado", exception.getMessage());
        Mockito.verify(taxRepository, Mockito.times(1)).findById(request.typeTaxId());
        Mockito.verifyNoInteractions(taxCalculationRepository);
    }

    @Test
    void testCalculateTaxWithNegativeAliquot() {
        // Arrange
        TaxCalculationRequestDTO request = new TaxCalculationRequestDTO(1L, 100.0);
        TaxEntity taxEntity = new TaxEntity(1L, TypeTax.ICMS, "Descrição", -10.0);

        Mockito.when(taxRepository.findById(request.typeTaxId())).thenReturn(Optional.of(taxEntity));

        // Act & Assert
        InvalidTaxAliquotException exception = Assertions.assertThrows(
                InvalidTaxAliquotException.class,
                () -> taxCalculationService.calculateTax(request),
                "Deveria lançar InvalidTaxAliquotException"
        );

        Assertions.assertEquals("Aliquota negativa não é permitida", exception.getMessage());
        Mockito.verify(taxRepository, Mockito.times(1)).findById(request.typeTaxId());
        Mockito.verifyNoInteractions(taxCalculationRepository);
    }

}