package br.com.mercadolivre.projetointegrador.marketplace.controller;

import br.com.mercadolivre.projetointegrador.marketplace.dto.CreateOrUpdateBatchDTO;
import br.com.mercadolivre.projetointegrador.marketplace.model.Batch;
import br.com.mercadolivre.projetointegrador.marketplace.service.BatchService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

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
}
