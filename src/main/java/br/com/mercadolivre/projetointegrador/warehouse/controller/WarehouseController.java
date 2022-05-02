package br.com.mercadolivre.projetointegrador.warehouse.controller;

import br.com.mercadolivre.projetointegrador.warehouse.assembler.WarehouseAssembler;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.CreateWarehousePayloadDTO;

import br.com.mercadolivre.projetointegrador.warehouse.dto.response.BatchResponseDTO;

import br.com.mercadolivre.projetointegrador.warehouse.dto.response.WarehouseResponseDTO;
import br.com.mercadolivre.projetointegrador.warehouse.exception.ErrorDTO;
import br.com.mercadolivre.projetointegrador.warehouse.mapper.WarehouseMapper;
import br.com.mercadolivre.projetointegrador.warehouse.model.Batch;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import br.com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import br.com.mercadolivre.projetointegrador.warehouse.service.WarehouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/warehouse")
@Tag(name = "Warehouse")
public class WarehouseController {

  private final WarehouseService warehouseService;
  private final WarehouseAssembler assembler;

  @Operation(summary = "CRIA UM NOVO ARMAZÉM", description = "Cria um novo armazém/depósito ")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Armazém criado com sucesso",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CreateWarehousePayloadDTO.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Dados invalidos!",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorDTO.class))
            })
      })
  @PostMapping
  public ResponseEntity<WarehouseResponseDTO> createWarehouse(
      @RequestBody @Valid CreateWarehousePayloadDTO payloadDTO) {
    Warehouse created =
        warehouseService.createWarehouse(WarehouseMapper.INSTANCE.createDtoToModel(payloadDTO));

    HttpHeaders headers = new HttpHeaders();
    headers.add(
        "Location",
        linkTo(methodOn(WarehouseController.class).findById(created.getId()))
            .withSelfRel()
            .toString());

    return assembler.toResponse(created, HttpStatus.CREATED, headers);
  }

  @GetMapping("/{id}")
  public ResponseEntity<WarehouseResponseDTO> findById(@PathVariable Long id) {
    Warehouse warehouse = warehouseService.findWarehouse(id);

    return assembler.toResponse(warehouse, HttpStatus.CREATED, null);
  }

  @GetMapping("/fresh-products/duedate")
  public /*ResponseEntity<List<BatchResponseDTO>>*/ResponseEntity<List<Batch>> findDueDateBatches(@RequestParam(name = "numb_days") String numberOfDays, @RequestParam(name = "section_id") String sectionId){
    List<Batch> section = warehouseService.dueDateBatches(Long.parseLong(numberOfDays), Long.parseLong(sectionId));
    return ResponseEntity.ok(section);
  }

}
