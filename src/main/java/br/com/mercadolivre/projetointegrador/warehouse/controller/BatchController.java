package br.com.mercadolivre.projetointegrador.warehouse.controller;

import br.com.mercadolivre.projetointegrador.warehouse.assembler.BatchAssembler;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.BatchResponseDTO;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.NotFoundException;
import br.com.mercadolivre.projetointegrador.warehouse.model.Batch;
import br.com.mercadolivre.projetointegrador.warehouse.service.BatchService;
import br.com.mercadolivre.projetointegrador.warehouse.view.BatchView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
public class BatchController {

  private final BatchService batchService;
  private final BatchAssembler assembler;

  @GetMapping("/api/v1/batches/{id}")
  public ResponseEntity<Batch> findBatchById(@PathVariable Long id) throws NotFoundException {
    return ResponseEntity.ok(batchService.findById(id));
  }

  @GetMapping("/api/v1/batches/ad/{sellerId}")
  @JsonView(BatchView.BatchAd.class)
  public ResponseEntity<List<BatchResponseDTO>> listBatchesToAd(@PathVariable Long sellerId){
    List<Batch> batchList = batchService.listBySellerId(sellerId);

    return assembler.toBatchResponse(batchList, HttpStatus.OK);
  }
}
