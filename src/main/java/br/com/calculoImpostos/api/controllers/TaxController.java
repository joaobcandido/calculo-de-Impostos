package br.com.calculoImpostos.api.controllers;


import br.com.calculoImpostos.api.dtos.TaxRequestDTO;
import br.com.calculoImpostos.api.dtos.TaxResponseDTO;
import br.com.calculoImpostos.api.services.TaxService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipos")
public class TaxController {
    private final TaxService taxService;

    public TaxController(TaxService taxService) {
        this.taxService = taxService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaxResponseDTO> searchTaxById(@PathVariable Long id) {
        return ResponseEntity.ok(taxService.searchTaxById(id));
    }
    @GetMapping()
    public ResponseEntity<List<TaxResponseDTO>> searchAllTaxes() {
        return ResponseEntity.ok(taxService.searchAllTaxes());
    }


    @PostMapping
    public ResponseEntity<TaxResponseDTO> registerTax(@Valid @RequestBody TaxRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(taxService.registerTax(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTax(@PathVariable Long id) {
        taxService.deleteTax(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
