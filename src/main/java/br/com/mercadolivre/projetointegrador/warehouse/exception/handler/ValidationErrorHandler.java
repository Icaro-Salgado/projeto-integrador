package br.com.mercadolivre.projetointegrador.warehouse.exception.handler;

import br.com.mercadolivre.projetointegrador.warehouse.exception.ErrorDTO;
import br.com.mercadolivre.projetointegrador.warehouse.exception.StandardError;
import br.com.mercadolivre.projetointegrador.warehouse.exception.validators.InboundOrderInvalidManagerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ValidationErrorHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleError(MethodArgumentNotValidException ex) {
    List<ObjectError> errorsMessages = ex.getBindingResult().getAllErrors();
    Map<String, String> errors = new HashMap<>();

    errorsMessages.forEach(
        err -> {
          String fieldName = ((FieldError) err).getField();
          errors.put(fieldName, err.getDefaultMessage());
        });

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

  @ExceptionHandler(InboundOrderInvalidManagerException.class)
  public ResponseEntity<StandardError> invalidManager(
      InboundOrderInvalidManagerException ex, HttpServletRequest request) {
    StandardError err = new StandardError();
    HttpStatus notModified = HttpStatus.BAD_REQUEST;

    err.setTimestamp(Instant.now());
    err.setStatus(notModified.value());
    err.setError(err.getError());
    err.setMessage(ex.getMessage());
    err.setPath(request.getRequestURI());

    return ResponseEntity.status(notModified).body(err);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorDTO> invalidArgument(IllegalArgumentException ex) {
    ErrorDTO errorDTO = new ErrorDTO();

    errorDTO.setError("Argumentos inválidos");
    errorDTO.setMessage(ex.getMessage());

    return ResponseEntity.badRequest().body(errorDTO);
  }
}
