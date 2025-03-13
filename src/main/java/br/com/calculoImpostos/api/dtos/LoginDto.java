package br.com.calculoImpostos.api.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(
        @NotBlank(message = "O campo username é obrigatorio")
        String username,
        @NotBlank(message = "O campo password é obrigatorio")
        String password
) {

}
