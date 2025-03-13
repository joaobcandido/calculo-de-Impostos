package br.com.calculoImpostos.api.dtos;


import jakarta.validation.constraints.NotBlank;

public record RegisterUserDto(
        @NotBlank(message = "O campo username é obrigatorio")
        String username,
        @NotBlank(message = "O campo password é obrigatorio")
        String password,
        @NotBlank(message = "O campo role é obrigatorio")
        String role
) {
}