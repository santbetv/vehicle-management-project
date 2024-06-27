package com.vehiculos.unow.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.UnknownHostException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {

    private static final String CODIGO_ERROR ="001";
    private static final String CODIGO_ERROR_VALIDATION ="002";
    private static final String ERROR_VALIDACION = "Error de validación";
    private static final String ERROR_VALIDACION_NO_EXISTE = "Error de validación, cliente no existe";

    @ExceptionHandler(UnknownHostException.class)
    public ResponseEntity<ApiExeceptionResponse> handleUnknownHostException(UnknownHostException ex) {
        ApiExeceptionResponse response = new ApiExeceptionResponse("","Error de conexion", "error-1024", ex.getMessage());
        return new ResponseEntity(response, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(BussinesRuleValidationException.class)
    public ResponseEntity<ApiExeceptionResponse> handleBussinesRuleValidationException(BussinesRuleValidationException ex) {

        List<String> errors = ex.getResult().getFieldErrors().stream()
                .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                .collect(Collectors.toList());

        ApiExeceptionResponse response = new ApiExeceptionResponse(ex.getType(),ERROR_VALIDACION, CODIGO_ERROR_VALIDATION,
                errors.toString());
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }
}
