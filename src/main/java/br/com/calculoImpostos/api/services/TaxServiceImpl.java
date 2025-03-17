package br.com.calculoImpostos.api.services;


import br.com.calculoImpostos.api.dtos.TaxRequestDTO;
import br.com.calculoImpostos.api.dtos.TaxResponseDTO;
import br.com.calculoImpostos.api.exceptions.TaxNotFoundException;
import br.com.calculoImpostos.api.mappers.TaxMapper;
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
    private final TaxValidationService taxValidationService;
    private final TaxMapper taxMapper;

    public TaxServiceImpl(TaxRepository taxRepository, TaxCalculationRepository taxCalculationRepository, TaxValidationService taxValidationService, TaxMapper taxMapper) {
        this.taxRepository = taxRepository;
        this.taxCalculationRepository = taxCalculationRepository;
        this.taxValidationService = taxValidationService;
        this.taxMapper = taxMapper;
    }

    @Override
    public TaxResponseDTO registerTax(TaxRequestDTO request) {
        taxValidationService.validateTaxName(request.name());
        TaxEntity tax = taxMapper.toEntity(request);
        TaxEntity savedTax = taxRepository.save(tax);
        return taxMapper.toResponseDTO(savedTax);
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
        return taxMapper.toResponseDTO(tax);
    }

    @Override
    public List<TaxResponseDTO> searchAllTaxes() {
        return taxRepository.findAll()
                .stream()
                .map(taxMapper::toResponseDTO) // Usando o TaxMapper
                .collect(Collectors.toList());
    }

}
