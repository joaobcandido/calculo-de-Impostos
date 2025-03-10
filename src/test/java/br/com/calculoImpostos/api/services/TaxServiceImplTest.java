package br.com.calculoImpostos.api.services;

import br.com.calculoImpostos.api.dtos.TaxRequestDTO;
import br.com.calculoImpostos.api.dtos.TaxResponseDTO;
import br.com.calculoImpostos.api.enums.TypeTax;
import br.com.calculoImpostos.api.exceptions.TaxAlreadyRegisteredException;
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

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TaxServiceImplTest {

    @Mock
    private TaxRepository taxRepository;
    @Mock
    private TaxCalculationRepository taxCalculationRepository;

    @InjectMocks
    private TaxServiceImpl taxService;

    @Test
    void testRegisterTaxSuccessfully() {
        // Arrange
        TaxRequestDTO request = new TaxRequestDTO(TypeTax.ICMS, "Imposto sobre Circulação de Mercadorias e Serviços", 18.0);
        TaxEntity taxEntity = new TaxEntity(null, TypeTax.ICMS, "Imposto sobre Circulação de Mercadorias e Serviços", 18.0);
        TaxEntity savedTax = new TaxEntity(1L, TypeTax.ICMS, "Imposto sobre Circulação de Mercadorias e Serviços", 18.0);

        Mockito.when(taxRepository.findByName(request.name())).thenReturn(Optional.empty());
        Mockito.when(taxRepository.save(Mockito.any(TaxEntity.class))).thenReturn(savedTax);

        // Act
        TaxResponseDTO response = taxService.registerTax(request);

        // Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1L, response.id());
        Assertions.assertEquals(TypeTax.ICMS, response.name());
    }

    @Test
    void testThrowExceptionWhenTaxNameAlreadyExists() {
        // Arrange
        TaxRequestDTO request = new TaxRequestDTO(TypeTax.ICMS, "Imposto sobre Circulação de Mercadorias e Serviços", 18.0);
        TaxEntity existingTax = new TaxEntity(1L, TypeTax.ICMS, "Imposto sobre Circulação de Mercadorias e Serviços", 18.0);

        Mockito.when(taxRepository.findByName(request.name())).thenReturn(Optional.of(existingTax));

        // Act & Assert
        Assertions.assertThrows(TaxAlreadyRegisteredException.class, () -> taxService.registerTax(request));
    }

    @Test
    void testThrowExceptionWhenDeletingNonExistentTax() {
        // Arrange
        Long taxId = 1L;

        Mockito.when(taxRepository.existsById(taxId)).thenReturn(false);

        // Act & Assert
        Assertions.assertThrows(TaxNotFoundException.class, () -> taxService.deleteTax(taxId));
    }

    @Test
    void testReturnTaxById() {
        // Arrange
        Long taxId = 1L;
        TaxEntity taxEntity = new TaxEntity(taxId, TypeTax.ICMS, "Imposto sobre Circulação de Mercadorias e Serviços", 15.0);

        Mockito.when(taxRepository.findById(taxId)).thenReturn(Optional.of(taxEntity));

        // Act
        TaxResponseDTO response = taxService.searchTaxById(taxId);

        // Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(taxId, response.id());
    }

    @Test
    void testThrowExceptionWhenTaxNotFoundById() {
        // Arrange
        Long taxId = 1L;

        Mockito.when(taxRepository.findById(taxId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(TaxNotFoundException.class, () -> taxService.searchTaxById(taxId));
    }

    @Test
    void testReturnAllTaxes() {
        // Arrange
        List<TaxEntity> taxes = List.of(
                new TaxEntity(1L, TypeTax.ICMS, "Imposto sobre Circulação de Mercadorias e Serviços", 15.0),
                new TaxEntity(2L, TypeTax.IPI, "Imposto sobre Produtos Industrializados", 10.0),
                new TaxEntity(3L, TypeTax.ISS, "Imposto sobre Serviços de Qualquer Natureza", 5.0)
        );

        Mockito.when(taxRepository.findAll()).thenReturn(taxes);

        // Act
        List<TaxResponseDTO> response = taxService.searchAllTaxes();

        // Assert
        Assertions.assertEquals(3, response.size());
    }

    @Test
    void testDeleteTaxWithMultipleCalculations() {
        // Arrange
        Long taxId = 1L;

        Mockito.when(taxRepository.existsById(taxId)).thenReturn(true);

        TaxCalculationEntity calculation1 = new TaxCalculationEntity(1L, null, 100.0, 10.0);
        TaxCalculationEntity calculation2 = new TaxCalculationEntity(2L, null, 200.0, 20.0);
        List<TaxCalculationEntity> calculations = List.of(calculation1, calculation2);

        Mockito.when(taxCalculationRepository.findByTaxId(taxId)).thenReturn(calculations);

        // Act
        taxService.deleteTax(taxId);

        // Assert
        Mockito.verify(taxCalculationRepository, Mockito.times(1)).save(calculation1);
        Mockito.verify(taxCalculationRepository, Mockito.times(1)).save(calculation2);
        Mockito.verify(taxCalculationRepository, Mockito.times(1)).deleteByTaxId(taxId);
        Mockito.verify(taxRepository, Mockito.times(1)).deleteById(taxId);
    }

    @Test
    void testDeleteTaxWhenTaxNotFound() {
        // Arrange
        Long taxId = 1L;

        Mockito.when(taxRepository.existsById(taxId)).thenReturn(false);

        // Act & Assert
        TaxNotFoundException exception = Assertions.assertThrows(
                TaxNotFoundException.class,
                () -> taxService.deleteTax(taxId),
                "Deveria lançar TaxNotFoundException"
        );

        Assertions.assertEquals("Imposto com ID " + taxId + " não encontrado.", exception.getMessage());
        Mockito.verifyNoInteractions(taxCalculationRepository);
        Mockito.verify(taxRepository, Mockito.never()).deleteById(Mockito.anyLong());
    }


    @Test
    void testSearchTaxByIdSuccessfully() {
        // Arrange
        Long taxId = 1L;
        TaxEntity taxEntity = new TaxEntity(taxId, TypeTax.ICMS, "Descrição", 10.0);
        Mockito.when(taxRepository.findById(taxId)).thenReturn(Optional.of(taxEntity));

        // Act
        TaxResponseDTO response = taxService.searchTaxById(taxId);

        // Assert
        Assertions.assertNotNull(response, "A resposta não deve ser nula.");
        Assertions.assertEquals(taxId, response.id(), "O ID deve ser igual ao esperado.");
        Assertions.assertEquals(TypeTax.ICMS, response.name(), "O tipo de imposto deve ser ICMS.");
        Assertions.assertEquals("Descrição", response.description(), "A descrição deve ser igual à esperada.");
        Assertions.assertEquals(10.0, response.aliquot(), "A alíquota deve ser 10.0.");
        Mockito.verify(taxRepository, Mockito.times(1)).findById(taxId);
    }


}