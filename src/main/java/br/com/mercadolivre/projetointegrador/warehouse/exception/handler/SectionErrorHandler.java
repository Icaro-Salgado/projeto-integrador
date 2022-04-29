package br.com.mercadolivre.projetointegrador.warehouse.exception.handler;

import br.com.mercadolivre.projetointegrador.warehouse.exception.StandardError;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.SectionDoesNotMatchWithProductException;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.SectionNotFoundException;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.SectionTotalCapacityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@RestControllerAdvice
public class SectionErrorHandler {

  @ExceptionHandler(value = SectionNotFoundException.class)
  protected ResponseEntity<StandardError> handleSectionNotFoundException(
      SectionNotFoundException e, HttpServletRequest request) {

    StandardError err = new StandardError();
    HttpStatus notFound = HttpStatus.NOT_FOUND;

    err.setTimestamp(Instant.now());
    err.setStatus(notFound.value());
    err.setError("Setor não encontrado");
    err.setMessage(e.getMessage());
    err.setPath(request.getRequestURI());
    return ResponseEntity.status(notFound).body(err);
  }

  @ExceptionHandler(value = SectionTotalCapacityException.class)
  protected ResponseEntity<StandardError> handleSectionTotalCapacityException(
      SectionTotalCapacityException e, HttpServletRequest request) {
    StandardError err = new StandardError();
    HttpStatus status = HttpStatus.BAD_REQUEST;

    err.setTimestamp(Instant.now());
    err.setStatus(status.value());
    err.setError("A capacidade do setor já foi atingida");
    err.setMessage(e.getMessage());
    err.setPath(request.getRequestURI());
    return ResponseEntity.status(status).body(err);
  }

  @ExceptionHandler(value = SectionDoesNotMatchWithProductException.class)
  protected ResponseEntity<StandardError> handleSectionDoesNotMatchWithProductException(
      SectionDoesNotMatchWithProductException e, HttpServletRequest request) {

    StandardError err = new StandardError();
    HttpStatus status = HttpStatus.BAD_REQUEST;

    err.setTimestamp(Instant.now());
    err.setStatus(status.value());
    err.setError("O setor não corresponde à categoria do produto");
    err.setMessage(e.getMessage());
    err.setPath(request.getRequestURI());
    return ResponseEntity.status(status).body(err);
  }
}
