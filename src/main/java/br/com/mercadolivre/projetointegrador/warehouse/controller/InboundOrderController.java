package br.com.mercadolivre.projetointegrador.warehouse.controller;

import br.com.mercadolivre.projetointegrador.warehouse.dto.response.BatchResponseDTO;
import br.com.mercadolivre.projetointegrador.warehouse.exception.ErrorDTO;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.NotFoundException;
import br.com.mercadolivre.projetointegrador.warehouse.model.Batch;
import br.com.mercadolivre.projetointegrador.warehouse.assembler.BatchAssembler;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.InboundOrderDTO;
import br.com.mercadolivre.projetointegrador.warehouse.mapper.InboundOrderMapper;
import br.com.mercadolivre.projetointegrador.warehouse.model.AppUser;
import br.com.mercadolivre.projetointegrador.warehouse.model.InboundOrder;
import br.com.mercadolivre.projetointegrador.warehouse.service.WarehouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inboundorder")
@RequiredArgsConstructor
@Tag(name = "Inbound Order")
public class InboundOrderController {

  private final WarehouseService warehouseService;
  private final InboundOrderMapper inboundOrderMapper;
  private final BatchAssembler assembler;

  @Operation(
      summary = "ADICIONA UMA NOVA INBOUND ORDER",
      description = "Cria uma nova ordem de alocação ")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Ordem criada",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = BatchResponseDTO.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Armazem ou Setor não encontrados!",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorDTO.class))
            }),
        @ApiResponse(
            responseCode = "409",
            description = "Lotes duplicados!",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorDTO.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description =
                "A capacidade do setor já foi atingida ou o produto não condiz com a sessão!",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorDTO.class))
            })
      })
  @PostMapping
  public ResponseEntity<List<BatchResponseDTO>> addInboundOrder(
      @RequestBody InboundOrderDTO dto, Authentication authentication) throws NotFoundException {
    AppUser requestUser = (AppUser) authentication.getPrincipal();
    InboundOrder inboundOrderToSave = inboundOrderMapper.toModel(dto);
    inboundOrderToSave.setManagerId(requestUser.getId());

    List<Batch> savedBatches = warehouseService.saveBatchInSection(inboundOrderToSave);

    return assembler.toCreatedResponse(savedBatches);
  }

  @Operation(
      summary = "ATUALIZA UMA INBOUND ORDER",
      description = "Atualiza uma ordem de alocação ")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Ordem Atualizada",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = BatchResponseDTO.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Armazem ou Setor não encontrados!",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorDTO.class))
            }),
        @ApiResponse(
            responseCode = "409",
            description = "Lotes duplicados!",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorDTO.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description =
                "A capacidade do setor já foi atingida ou o produto não condiz com a sessão!",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorDTO.class))
            })
      })
  @PutMapping
  public ResponseEntity<List<BatchResponseDTO>> updateInboundOrder(
      @RequestBody InboundOrderDTO dto, Authentication authentication) throws NotFoundException {
    InboundOrder inboundOrderToUpdate = inboundOrderMapper.toModel(dto);
    AppUser requestUser = (AppUser) authentication.getPrincipal();
    inboundOrderToUpdate.setManagerId(requestUser.getId());
    List<Batch> updatedBatches = warehouseService.updateBatchInSection(inboundOrderToUpdate);

    return assembler.toCreatedResponse(updatedBatches);
  }
}
