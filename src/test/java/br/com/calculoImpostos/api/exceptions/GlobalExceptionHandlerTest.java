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
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleTaxNotFoundException(exception);

        // Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatusCode());
        Assertions.assertTrue(response.getBody().getMessages().contains(errorMessage));
    }

    @Test
    void testHandleGenericException() {
        // Arrange
        String expectedMessage = "Ocorreu um erro inesperado.";
        Exception exception = new Exception("Erro genérico");

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGenericException(exception);

        // Assert
        Assertions.assertNotNull(response, "A resposta não deve ser nula.");
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode(), "O status HTTP deve ser INTERNAL_SERVER_ERROR.");
        Assertions.assertNotNull(response.getBody(), "O corpo da resposta não deve ser nulo.");
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatusCode(), "O código de status deve ser 500.");
        Assertions.assertTrue(response.getBody().getMessages().contains(expectedMessage), "A lista de mensagens deve conter a mensagem esperada.");
    }

    @Test
    void testHandleTaxAlreadyRegisteredException() {
        // Arrange
        String expectedMessage = "Imposto com nome ICMS já está cadastrado.";
        TaxAlreadyRegisteredException exception = new TaxAlreadyRegisteredException(expectedMessage);

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleTaxAlreadyRegisteredException(exception);

        // Assert
        Assertions.assertNotNull(response, "A resposta não deve ser nula.");
        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode(), "O status HTTP deve ser CONFLICT.");
        Assertions.assertNotNull(response.getBody(), "O corpo da resposta não deve ser nulo.");
        Assertions.assertEquals(HttpStatus.CONFLICT.value(), response.getBody().getStatusCode(), "O código de status deve ser 409.");
        Assertions.assertTrue(response.getBody().getMessages().contains(expectedMessage), "A lista de mensagens deve conter a mensagem esperada.");
    }
}