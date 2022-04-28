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
    err.setError("Section not found");
    err.setMessage(e.getMessage());
    err.setPath(request.getRequestURI());
    return ResponseEntity.status(notFound).body(err);
  }

  @ExceptionHandler(value = SectionTotalCapacityException.class)
  protected ResponseEntity<StandardError> handleSectionTotalCapacityException(
      SectionTotalCapacityException e, HttpServletRequest request) {
    StandardError err = new StandardError();
    HttpStatus notModified = HttpStatus.NOT_MODIFIED;

    err.setTimestamp(Instant.now());
    err.setStatus(notModified.value());
    err.setError("Section Capacity is already Full");
    err.setMessage(e.getMessage());
    err.setPath(request.getRequestURI());
    return ResponseEntity.status(notModified).body(err);
  }

  @ExceptionHandler(value = SectionDoesNotMatchWithProductException.class)
  protected ResponseEntity<StandardError> handleSectionDoesNotMatchWithProductException(
      SectionDoesNotMatchWithProductException e, HttpServletRequest request) {

    StandardError err = new StandardError();
    HttpStatus notModified = HttpStatus.NOT_MODIFIED;

    err.setTimestamp(Instant.now());
    err.setStatus(notModified.value());
    err.setError("Section does not match with product category");
    err.setMessage(e.getMessage());
    err.setPath(request.getRequestURI());
    return ResponseEntity.status(notModified).body(err);
  }
}
