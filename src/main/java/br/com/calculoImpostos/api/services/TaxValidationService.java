package br.com.calculoImpostos.api.services;

import br.com.calculoImpostos.api.enums.TypeTax;
import br.com.calculoImpostos.api.exceptions.TaxAlreadyRegisteredException;
import br.com.calculoImpostos.api.repositories.TaxRepository;
import org.springframework.stereotype.Service;

@Service
public class TaxValidationService {

    private final TaxRepository taxRepository;

    public TaxValidationService(TaxRepository taxRepository) {
        this.taxRepository = taxRepository;
    }

    public void validateTaxName(TypeTax name) {
        if (taxRepository.findByName(name).isPresent()) {
            throw new TaxAlreadyRegisteredException("Imposto com nome " + name + " já está cadastrado.");
        }
    }
}
