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
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaxServiceImpl implements TaxService {

    private final TaxRepository taxRepository;
    private final TaxCalculationRepository taxCalculationRepository;

    public TaxServiceImpl(TaxRepository taxRepository, TaxCalculationRepository taxCalculationRepository) {
        this.taxRepository = taxRepository;
        this.taxCalculationRepository = taxCalculationRepository;
    }

    @Override
    public TaxResponseDTO registerTax(TaxRequestDTO request) {
        validateTaxName(request.name());
        TaxEntity tax = mapToModel(request);
        TaxEntity savedTax = taxRepository.save(tax);
        return mapToResponseDTO(savedTax);
    }

    @Override
    @Transactional
    public void deleteTax(Long id) {
        if (!taxRepository.existsById(id)) {
            throw new TaxNotFoundException("Imposto com ID " + id + " não encontrado.");
        }
        List<TaxCalculationEntity> calculations = taxCalculationRepository.findByTaxId(id);
        for (TaxCalculationEntity calculation : calculations) {
            calculation.setTax(null);
            taxCalculationRepository.save(calculation);
        }
        taxCalculationRepository.deleteByTaxId(id);
        taxRepository.deleteById(id);
    }

    @Override
    public TaxResponseDTO searchTaxById(Long id) {
        TaxEntity tax = taxRepository.findById(id)
                .orElseThrow(() -> new TaxNotFoundException("Imposto com ID " + id + " não encontrado."));
        return mapToResponseDTO(tax);
    }

    @Override
    public List<TaxResponseDTO> searchAllTaxes() {
        return taxRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private TaxEntity mapToModel(TaxRequestDTO request) {
        return new TaxEntity(
                null,
                request.name(),
                request.description(),
                request.aliquot());
    }

    private TaxResponseDTO mapToResponseDTO(TaxEntity tax) {
        return new TaxResponseDTO(
                tax.getId(),
                tax.getName(),
                tax.getDescription(),
                tax.getAliquot());
    }

    private void validateTaxName(TypeTax name) {
        if (taxRepository.findByName(name).isPresent()) {
            throw new TaxAlreadyRegisteredException("Imposto com nome " + name + " já está cadastrado.");
        }
    }
}
