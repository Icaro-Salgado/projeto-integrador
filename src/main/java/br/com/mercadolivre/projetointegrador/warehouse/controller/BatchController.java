package br.com.mercadolivre.projetointegrador.warehouse.controller;

import br.com.mercadolivre.projetointegrador.warehouse.assembler.BatchAssembler;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.BatchResponseDTO;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.NotFoundException;
import br.com.mercadolivre.projetointegrador.warehouse.service.BatchService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BatchController {

  private final BatchService batchService;
  private final BatchAssembler assembler;

  @GetMapping("/api/v1/batches/{id}")
  public ResponseEntity<BatchResponseDTO> findBatchById(@PathVariable Long id) throws NotFoundException {
    return assembler.toResponse(batchService.findById(id), HttpStatus.OK);
  }
}
