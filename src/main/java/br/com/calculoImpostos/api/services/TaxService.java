package br.com.calculoImpostos.api.services;


import br.com.calculoImpostos.api.dtos.TaxRequestDTO;
import br.com.calculoImpostos.api.dtos.TaxResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaxService {

    TaxResponseDTO searchTaxById(Long id);

    List<TaxResponseDTO> searchAllTaxes();

    TaxResponseDTO registerTax(TaxRequestDTO request);

    void deleteTax(Long id);

}
