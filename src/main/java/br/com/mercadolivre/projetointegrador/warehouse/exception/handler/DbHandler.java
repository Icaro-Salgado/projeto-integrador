package br.com.mercadolivre.projetointegrador.warehouse.exception.handler;

import br.com.mercadolivre.projetointegrador.warehouse.exception.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;


@RestControllerAdvice
public class DbHandler {

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorDTO> handler(SQLException ex){
        String message = Arrays.stream(ex.getMessage().split("Detalhe: ")).collect(Collectors.toList()).get(1);

        ErrorDTO error = new ErrorDTO();
        error.setError("Ocorreu um erro");

        error.setMessage(message.contains("already exists") ? translateDuplicateValueMessage(message) : message);

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    private String translateDuplicateValueMessage(String message){
        return message.replace("already exists", "já existe na base").replace("Key", "Campos");
    }

}
