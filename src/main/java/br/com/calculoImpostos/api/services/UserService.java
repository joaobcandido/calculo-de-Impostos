package br.com.calculoImpostos.api.services;


import br.com.calculoImpostos.api.dtos.RegisterUserDto;

public interface UserService {
    void registerUser(RegisterUserDto registerUserDto);

}
