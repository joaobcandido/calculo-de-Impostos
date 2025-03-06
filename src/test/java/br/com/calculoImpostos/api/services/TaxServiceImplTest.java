package br.com.calculoImpostos.api.services;

import br.com.calculoImpostos.api.dtos.TaxRequestDTO;
import br.com.calculoImpostos.api.enums.TypeTax;
import br.com.calculoImpostos.api.dtos.TaxResponseDTO;
import br.com.calculoImpostos.api.exceptions.TaxNotFoundException;
import br.com.calculoImpostos.api.exceptions.TaxAlreadyRegisteredException;
import br.com.calculoImpostos.api.models.TaxModel;
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

    @InjectMocks
    private TaxServiceImpl taxService;

    @Test
    void testRegisterTaxSuccessfully() {
         // Arrange
        TaxRequestDTO request = new TaxRequestDTO(TypeTax.ICMS, "Imposto sobre Circulação de Mercadorias e Serviços", 18.0);
        TaxModel taxModel = new TaxModel(null, TypeTax.ICMS, "Imposto sobre Circulação de Mercadorias e Serviços", 18.0);
        TaxModel savedTax = new TaxModel(1L, TypeTax.ICMS, "Imposto sobre Circulação de Mercadorias e Serviços", 18.0);

        Mockito.when(taxRepository.findByName(request.name())).thenReturn(Optional.empty());
        Mockito.when(taxRepository.save(Mockito.any(TaxModel.class))).thenReturn(savedTax);

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
        TaxModel existingTax = new TaxModel(1L, TypeTax.ICMS, "Imposto sobre Circulação de Mercadorias e Serviços", 18.0);

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
        TaxModel taxModel = new TaxModel(taxId, TypeTax.ICMS, "Imposto sobre Circulação de Mercadorias e Serviços", 15.0);

        Mockito.when(taxRepository.findById(taxId)).thenReturn(Optional.of(taxModel));

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
        List<TaxModel> taxes = List.of(
                new TaxModel(1L, TypeTax.ICMS, "Imposto sobre Circulação de Mercadorias e Serviços", 15.0),
                new TaxModel(2L, TypeTax.IPI, "Imposto sobre Produtos Industrializados", 10.0),
                new TaxModel(3L, TypeTax.ISS, "Imposto sobre Serviços de Qualquer Natureza", 5.0)
        );

        Mockito.when(taxRepository.findAll()).thenReturn(taxes);

        // Act
        List<TaxResponseDTO> response = taxService.searchAllTaxes();

        // Assert
        Assertions.assertEquals(3, response.size());
    }
    @Test
    void testDeleteTaxSuccessfully() {
        // Arrange
        Long taxId = 1L;
        Mockito.when(taxRepository.existsById(taxId)).thenReturn(true);

        // Act
        taxService.deleteTax(taxId);

        // Assert
        Mockito.verify(taxRepository, Mockito.times(1)).deleteById(taxId);
    }


}