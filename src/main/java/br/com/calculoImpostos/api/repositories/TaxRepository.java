package br.com.calculoImpostos.api.repositories;


import br.com.calculoImpostos.api.enums.TypeTax;
import br.com.calculoImpostos.api.models.TaxModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TaxRepository extends JpaRepository<TaxModel, Long> {

    Optional<TaxModel> findByName(TypeTax name);

}
