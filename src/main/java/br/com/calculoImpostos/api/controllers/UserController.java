package br.com.calculoImpostos.api.controllers;


import br.com.calculoImpostos.api.dtos.AuthResponseDto;
import br.com.calculoImpostos.api.dtos.LoginDto;
import br.com.calculoImpostos.api.dtos.RegisterUserDto;
import br.com.calculoImpostos.api.jwt.JwtTokenProvider;
import br.com.calculoImpostos.api.services.AuthService;
import br.com.calculoImpostos.api.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/usuario")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/registrar")
    public void registerUser(@Valid @RequestBody RegisterUserDto registerUserDto) {
        userService.registerUser(registerUserDto);
    }

    @PostMapping("/logar")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);
        AuthResponseDto authResponseDto = new AuthResponseDto(token);

        return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
    }

}