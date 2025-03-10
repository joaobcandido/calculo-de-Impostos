package br.com.calculoImpostos.api.repositories;


import br.com.calculoImpostos.api.models.TaxCalculationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaxCalculationRepository extends JpaRepository<TaxCalculationEntity, Long> {
    List<TaxCalculationEntity> findByTaxId(Long id);
    void deleteByTaxId(Long taxId);

}
