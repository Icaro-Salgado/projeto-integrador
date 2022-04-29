package br.com.mercadolivre.projetointegrador.warehouse.controller;

import br.com.mercadolivre.projetointegrador.warehouse.dto.request.LoginDTO;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.CreatedBatchDTO;
import br.com.mercadolivre.projetointegrador.warehouse.exception.ErrorDTO;
import br.com.mercadolivre.projetointegrador.warehouse.exception.StandardError;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.NotFoundException;
import br.com.mercadolivre.projetointegrador.warehouse.model.Batch;
import br.com.mercadolivre.projetointegrador.warehouse.service.BatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Tag(name = "Lote")
public class BatchController {

    BatchService batchService;

    @Operation(summary = "RETORNA UM LOTE", description = "Retorna um lote com o id correspondente ao passado na url")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Lote retornado com sucesso",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CreatedBatchDTO.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Lote não encontrado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))})
    })
    @GetMapping("/api/v1/batches/{id}")
    public ResponseEntity<Batch> findBatchById(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(batchService.findById(id));
    }
}
