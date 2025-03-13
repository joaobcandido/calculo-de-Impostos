package br.com.calculoImpostos.api.models;

import br.com.calculoImpostos.api.enums.TypeTax;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(name = "taxes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private TypeTax name;

    @NotBlank()
    private String description;

    @NotNull
    @Positive
    private Double aliquot;

}


