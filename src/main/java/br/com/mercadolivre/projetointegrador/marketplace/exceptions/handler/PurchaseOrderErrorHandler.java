package br.com.mercadolivre.projetointegrador.marketplace.exceptions.handler;

import br.com.mercadolivre.projetointegrador.marketplace.exceptions.NotFoundException;
import br.com.mercadolivre.projetointegrador.warehouse.exception.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PurchaseOrderErrorHandler {
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorDTO> NotFound(Exception e) {
    ErrorDTO error = new ErrorDTO();
    error.setError("Não encontrado");
    error.setMessage(e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }
}
