package br.com.calculoImpostos.api.services;


import br.com.calculoImpostos.api.dtos.TaxCalculationRequestDTO;
import br.com.calculoImpostos.api.dtos.TaxCalculationResponseDTO;


public interface TaxCalculationService {
    TaxCalculationResponseDTO calculateTax(TaxCalculationRequestDTO request);
}
