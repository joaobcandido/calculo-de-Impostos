package br.com.calculoImpostos.api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.*;

@Entity
@Table(name = "calculations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxCalculationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "typeTaxId")
    private TaxEntity tax;

    @NotNull()
    @Positive()
    private double baseValue;
    @NotNull()
    @Positive()
    private double valueCalculated;

}
