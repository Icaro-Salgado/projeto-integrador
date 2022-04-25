package br.com.mercadolivre.projetointegrador.marketplace.controller;

import br.com.mercadolivre.projetointegrador.marketplace.dto.CreateOrUpdateBatchDTO;
import br.com.mercadolivre.projetointegrador.marketplace.exception.NotFoundException;
import br.com.mercadolivre.projetointegrador.marketplace.model.Batch;
import br.com.mercadolivre.projetointegrador.marketplace.service.BatchService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/batches")
public class BatchController {

    BatchService batchService;

    @PostMapping
    public ResponseEntity<Void> createBatch(
            @Valid @RequestBody CreateOrUpdateBatchDTO createorUpdateBatchDto,
            UriComponentsBuilder uriBuilder
    ) {
        Batch batch = createorUpdateBatchDto.mountBatch();
        batchService.createBatch(batch);

        URI uri = uriBuilder.path("/api/v1/batches/{id}").buildAndExpand(batch.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<List<Batch>> getAll() {
        return ResponseEntity.ok(batchService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBatch(
            @PathVariable Long id,
            @RequestBody Batch batch,
            UriComponentsBuilder uriBuilder) throws NotFoundException {

        batchService.updateBatch(id, batch);

        URI uri = uriBuilder.path("/api/v1/batches/{id}").buildAndExpand(batch.getId()).toUri();

        return ResponseEntity.noContent().location(uri).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBatch(@PathVariable Long id) throws NotFoundException {
        batchService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
