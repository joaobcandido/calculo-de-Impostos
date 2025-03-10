package br.com.calculoImpostos.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaxAlreadyRegisteredException.class)
    public ResponseEntity<ErrorResponse> handleTaxAlreadyRegisteredException(TaxAlreadyRegisteredException ex) {
        List<String> errors = List.of(ex.getMessage());

        return buildErrorResponse(HttpStatus.CONFLICT, errors);
    }

    @ExceptionHandler(TaxNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTaxNotFoundException(TaxNotFoundException ex) {
        List<String> errors = List.of(ex.getMessage());

        return buildErrorResponse(HttpStatus.NOT_FOUND, errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ex.printStackTrace();
        List<String> errors = List.of("Ocorreu um erro inesperado.");

        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, errors);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());

        return buildErrorResponse(HttpStatus.BAD_REQUEST, errors);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, List<String> messages) {
        ErrorResponse errorResponse = new ErrorResponse(status.value(), messages);
        return ResponseEntity.status(status).body(errorResponse);
    }

}
