package br.com.calculoImpostos.api.repositories;


import br.com.calculoImpostos.api.enums.TypeTax;
import br.com.calculoImpostos.api.models.TaxEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TaxRepository extends JpaRepository<TaxEntity, Long> {

    Optional<TaxEntity> findByName(TypeTax name);

}
