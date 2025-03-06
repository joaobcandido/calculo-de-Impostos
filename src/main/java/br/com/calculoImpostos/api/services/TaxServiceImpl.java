package br.com.calculoImpostos.api.services;


import br.com.calculoImpostos.api.dtos.TaxRequestDTO;
import br.com.calculoImpostos.api.dtos.TaxResponseDTO;
import br.com.calculoImpostos.api.enums.TypeTax;
import br.com.calculoImpostos.api.exceptions.TaxAlreadyRegisteredException;
import br.com.calculoImpostos.api.exceptions.TaxNotFoundException;
import br.com.calculoImpostos.api.models.TaxModel;
import br.com.calculoImpostos.api.repositories.TaxRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaxServiceImpl implements TaxService {

    private final TaxRepository taxRepository;

    public TaxServiceImpl(TaxRepository taxRepository) {
        this.taxRepository = taxRepository;
    }

    @Override
    public TaxResponseDTO registerTax(TaxRequestDTO request) {
        validateTaxName(request.name());
        TaxModel tax = mapToModel(request);
        TaxModel savedTax = taxRepository.save(tax);
        return mapToResponseDTO(savedTax);
    }

    @Override
    public void deleteTax(Long id) {
        if (!taxRepository.existsById(id)) {
            throw new TaxNotFoundException("Imposto com ID " + id + " não encontrado.");
        }
        taxRepository.deleteById(id);
    }

    @Override
    public TaxResponseDTO searchTaxById(Long id) {
        TaxModel tax = taxRepository.findById(id)
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

    private TaxModel mapToModel(TaxRequestDTO request) {
        return new TaxModel(
                null,
                request.name(),
                request.description(),
                request.aliquot());
    }

    private TaxResponseDTO mapToResponseDTO(TaxModel tax) {
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
