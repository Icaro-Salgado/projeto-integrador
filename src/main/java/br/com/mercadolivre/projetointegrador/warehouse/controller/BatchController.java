package br.com.mercadolivre.projetointegrador.warehouse.controller;

import br.com.mercadolivre.projetointegrador.warehouse.exception.ErrorDTO;
import br.com.mercadolivre.projetointegrador.warehouse.assembler.BatchAssembler;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.BatchResponseDTO;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.NotFoundException;
import br.com.mercadolivre.projetointegrador.warehouse.service.BatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Tag(name = "Batch")
public class BatchController {

  private final BatchService batchService;
  private final BatchAssembler assembler;

  @Operation(
      summary = "RETORNA UM LOTE",
      description = "Retorna um lote com o id correspondente ao passado na url")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lote retornado com sucesso",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = BatchResponseDTO.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Lote não encontrado",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorDTO.class))
            })
      })
  @GetMapping("/api/v1/batches/{id}")
  public ResponseEntity<BatchResponseDTO> findBatchById(@PathVariable Long id)
      throws NotFoundException {
    return assembler.toResponse(batchService.findById(id), HttpStatus.OK);
  }
}
