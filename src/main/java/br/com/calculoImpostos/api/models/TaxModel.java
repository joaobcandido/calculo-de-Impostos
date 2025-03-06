package br.com.calculoImpostos.api.models;

import br.com.calculoImpostos.api.enums.TypeTax;
import jakarta.persistence.*;

@Entity
@Table(name = "impostos")
public class TaxModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private TypeTax name;

    private String description;

    private Double aliquot;

    public TaxModel() {
    }

    public TaxModel(Long id, TypeTax name, String description, Double aliquot) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.aliquot = aliquot;
    }

    public Long getId() {
        return id;
    }

    public TypeTax getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getAliquot() {
        return aliquot;
    }
}


