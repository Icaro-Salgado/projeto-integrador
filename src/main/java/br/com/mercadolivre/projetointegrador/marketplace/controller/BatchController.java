package br.com.mercadolivre.projetointegrador.marketplace.controller;

import br.com.mercadolivre.projetointegrador.marketplace.exception.NotFoundException;
import br.com.mercadolivre.projetointegrador.marketplace.model.Batch;
import br.com.mercadolivre.projetointegrador.marketplace.service.BatchService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BatchController {

    BatchService batchService;

    @GetMapping("/api/v1/batches/{id}")
    public ResponseEntity<Batch> findBatchById(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(batchService.findById(id));
    }
}
