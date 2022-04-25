package br.com.mercadolivre.projetointegrador.marketplace.controller.advice;

import br.com.mercadolivre.projetointegrador.marketplace.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Void> notFoundHandler(NotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
