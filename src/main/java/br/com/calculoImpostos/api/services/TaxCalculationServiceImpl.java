package br.com.calculoImpostos.api.services;


import br.com.calculoImpostos.api.dtos.TaxCalculationRequestDTO;
import br.com.calculoImpostos.api.dtos.TaxCalculationResponseDTO;
import br.com.calculoImpostos.api.exceptions.InvalidTaxAliquotException;
import br.com.calculoImpostos.api.exceptions.TaxNotFoundException;
import br.com.calculoImpostos.api.models.TaxCalculationEntity;
import br.com.calculoImpostos.api.models.TaxEntity;
import br.com.calculoImpostos.api.repositories.TaxCalculationRepository;
import br.com.calculoImpostos.api.repositories.TaxRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TaxCalculationServiceImpl implements TaxCalculationService {
    private static final Logger logger = LoggerFactory.getLogger(TaxCalculationServiceImpl.class);
    private final TaxRepository taxRepository;
    private final TaxCalculationRepository taxCalculationRepository;

    public TaxCalculationServiceImpl(TaxRepository taxRepository, TaxCalculationRepository taxCalculationRepository) {
        this.taxRepository = taxRepository;
        this.taxCalculationRepository = taxCalculationRepository;
    }


    @Transactional
    public TaxCalculationResponseDTO calculateTax(TaxCalculationRequestDTO request) {
        logger.info("Iniciando cálculo de imposto para tipoImpostoId: {}", request.typeTaxId());

        TaxEntity taxId = taxRepository.findById(request.typeTaxId())
                .orElseThrow(() -> new TaxNotFoundException("Imposto nao encontrado"));

        if (taxId.getAliquot() < 0) {
            throw new InvalidTaxAliquotException("Aliquota negativa não é permitida");
        }

        TaxCalculationEntity calculation = new TaxCalculationEntity();
        calculation.setTax(taxId);
        calculation.setBaseValue(request.baseValue());

        double baseValue = calculation.getBaseValue();
        double aliquot = calculation.getTax().getAliquot();

        double valueTax = baseValue * (aliquot / 100.0);
        calculation.setValueCalculated(valueTax);

        taxCalculationRepository.save(calculation);

        TaxCalculationResponseDTO response = new TaxCalculationResponseDTO(
                calculation.getTax().getName().name(),
                calculation.getBaseValue(),
                calculation.getTax().getAliquot(),
                calculation.getValueCalculated()
        );

        logger.info("Cálculo de imposto concluído. Valor calculado: {}", valueTax);
        return response;
    }
}
