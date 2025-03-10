package br.com.calculoImpostos.api.controllers;


import br.com.calculoImpostos.api.dtos.TaxCalculationRequestDTO;
import br.com.calculoImpostos.api.dtos.TaxCalculationResponseDTO;
import br.com.calculoImpostos.api.repositories.TaxRepository;
import br.com.calculoImpostos.api.services.TaxCalculationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculo")
public class TaxCalculationController {

    private final TaxCalculationService taxCalculationService;
    private final TaxRepository taxRepository;

    public TaxCalculationController(TaxCalculationService taxCalculationService, TaxRepository taxRepository) {
        this.taxCalculationService = taxCalculationService;
        this.taxRepository = taxRepository;
    }


    @PostMapping
    public ResponseEntity<TaxCalculationResponseDTO> calculateTax(@Valid @RequestBody TaxCalculationRequestDTO request) {

        return ResponseEntity.status(HttpStatus.OK).body(taxCalculationService.calculateTax(request));


    }
}

