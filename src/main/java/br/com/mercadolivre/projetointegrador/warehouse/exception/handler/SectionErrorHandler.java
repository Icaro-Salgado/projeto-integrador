package br.com.mercadolivre.projetointegrador.warehouse.exception.handler;

import br.com.mercadolivre.projetointegrador.warehouse.exception.StandardError;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.SectionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@RestControllerAdvice
public class SectionErrorHandler {
    @ExceptionHandler(value = SectionNotFoundException.class)
    protected ResponseEntity<StandardError> handleSectionNotFoundException(SectionNotFoundException e, HttpServletRequest request){
        StandardError err = new StandardError();

        err.setTimestamp(Instant.now());
        err.setStatus(HttpStatus.NOT_FOUND.value());
        err.setError("Section not found");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }
}
