package br.com.calculoImpostos.api.dtos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthResponseDtoTest {
    @Test
    void testAuthResponseDto() {
        // Arrange
        String token = "testToken";

        // Act
        AuthResponseDto authResponseDto = new AuthResponseDto(token);

        // Assert
        Assertions.assertEquals(token, authResponseDto.token());
    }
}