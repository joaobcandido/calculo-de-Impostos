package br.com.calculoImpostos.api.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleTaxNotFoundException() {
        // Arrange
        String errorMessage = "Imposto com ID 1 não encontrado.";
        TaxNotFoundException exception = new TaxNotFoundException(errorMessage);

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleResourceNotFoundException(exception);

        // Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals(errorMessage, response.getBody().getMensagem());
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatusCode());
    }
    @Test
    void testHandleGenericException() {
        // Arrange
        String expectedMessage = "Ocorreu um erro inesperado.";
        Exception exception = new Exception("Erro genérico");

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGenericException(exception);

        // Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Assertions.assertEquals(expectedMessage, response.getBody().getMensagem());
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatusCode());
    }
    @Test
    void testHandleTaxAlreadyRegisteredException() {
        // Arrange
        String expectedMessage = "Imposto com nome ICMS já está cadastrado.";
        TaxAlreadyRegisteredException exception = new TaxAlreadyRegisteredException(expectedMessage);

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleResourceRegisteredException(exception);

        // Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        Assertions.assertEquals(expectedMessage, response.getBody().getMensagem());
        Assertions.assertEquals(HttpStatus.CONFLICT.value(), response.getBody().getStatusCode());
    }
}