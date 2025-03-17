package br.com.calculoImpostos.api.services;


import br.com.calculoImpostos.api.dtos.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);
}
