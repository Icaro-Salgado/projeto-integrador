package br.com.mercadolivre.projetointegrador.warehouse.controller;

import br.com.mercadolivre.projetointegrador.security.model.AppUser;
import br.com.mercadolivre.projetointegrador.warehouse.assembler.BatchAssembler;
import br.com.mercadolivre.projetointegrador.warehouse.assembler.SectionAssembler;
import br.com.mercadolivre.projetointegrador.warehouse.assembler.WarehouseAssembler;
import br.com.mercadolivre.projetointegrador.warehouse.docs.config.SecuredWarehouseRestController;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.CreateBatchPayloadDTO;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.CreateWarehousePayloadDTO;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.BatchResponseDTO;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.SectionBatchesDTO;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.WarehouseResponseDTO;
import br.com.mercadolivre.projetointegrador.warehouse.enums.CategoryEnum;
import br.com.mercadolivre.projetointegrador.warehouse.enums.SortTypeEnum;
import br.com.mercadolivre.projetointegrador.warehouse.exception.ErrorDTO;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.NotFoundException;
import br.com.mercadolivre.projetointegrador.warehouse.mapper.WarehouseMapper;
import br.com.mercadolivre.projetointegrador.warehouse.model.Batch;
import br.com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import br.com.mercadolivre.projetointegrador.warehouse.service.WarehouseService;
import br.com.mercadolivre.projetointegrador.warehouse.view.SectionView;
import com.fasterxml.jackson.annotation.JsonView;
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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/warehouse")
@Tag(name = "[Warehouse] - Warehouse")
public class WarehouseControllerWarehouse implements SecuredWarehouseRestController {

  private final WarehouseService warehouseService;
  private final WarehouseAssembler assembler;
  private final SectionAssembler sectionAssembler;
  private final BatchAssembler batchAssembler;

  @Operation(summary = "CRIA UM NOVO ARMAZÉM", description = "Cria um novo armazém/depósito ")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
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
        linkTo(methodOn(WarehouseControllerWarehouse.class).findById(created.getId()))
            .withSelfRel()
            .toString());

    return assembler.toResponse(created, HttpStatus.CREATED, headers);
  }

  @Operation(
      summary = "RETORNA UM ARMAZÉM CADASTRADO",
      description = "Retorna um armazém/depósito já cadastrado na base")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Retorno ok",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = WarehouseResponseDTO.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Armazém não encontrado",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorDTO.class))
            })
      })
  @GetMapping("/{id}")
  public ResponseEntity<WarehouseResponseDTO> findById(@PathVariable Long id) {
    Warehouse warehouse = warehouseService.findWarehouse(id);

    return assembler.toResponse(warehouse, HttpStatus.OK, null);
  }

  @Operation(
      summary = "RETORNA LOTES A VENCER DE UMA DADA SEÇÃO",
      description =
          "Retorna uma lista de lotes que se encontram em uma seção específica e que a váliidade"
              + " dos produtos é menor que os dias especificados")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Consulta realizada com sucesso",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CreateBatchPayloadDTO.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos!",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorDTO.class))
            })
      })
  @GetMapping("/fresh-products/duedate")
  public ResponseEntity<List<BatchResponseDTO>> findDueDateBatches(
      @RequestParam(name = "numb_days") String numberOfDays,
      @RequestParam(name = "section_id") String sectionId) {

    List<Batch> batches =
        warehouseService.dueDateBatches(Long.parseLong(numberOfDays), Long.parseLong(sectionId));
    return batchAssembler.toRespondOk(batches);
  }

  @Operation(
      summary = "RETORNA LOTES A VENCER COM PRODUTOS DE UMA DADA CATEGORIA",
      description =
          "Retorna uma lista de lotes que se encontram em uma determinada categoria, a válidade dos"
              + " produtos é menor que os dias especificados, e o resultado e ordenado de forma"
              + " crescente ou decrecente")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Consulta realizada com sucesso",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CreateBatchPayloadDTO.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos!",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorDTO.class))
            })
      })
  @GetMapping("/fresh-products/duedate-batches")
  public ResponseEntity<List<BatchResponseDTO>> findDueDateBatchesByCategory(
      @RequestParam(name = "numb_days") String numberOfDays,
      @RequestParam(name = "category") CategoryEnum category,
      @RequestParam(name = "order") String order) {

    List<Batch> batches =
        warehouseService.dueDateBatchesByCategory(Long.parseLong(numberOfDays), category, order);
    return batchAssembler.toRespondOk(batches);
  }

  @GetMapping("/fresh-products/list")
  @JsonView(SectionView.SectionWithBatches.class)
  public ResponseEntity<SectionBatchesDTO> listStockProducts(
      @RequestParam(required = false) Long product,
      @RequestParam(required = false, defaultValue = "L") SortTypeEnum sort,
      Authentication authentication)
      throws NotFoundException {
    AppUser requestUser = (AppUser) authentication.getPrincipal();
    Long managerId = requestUser.getId();

    if (product == null) {
      throw new IllegalArgumentException("Informe o ID do produto");
    }

    List<Batch> batchProducts =
        warehouseService.findProductOnManagerSection(managerId, product, sort);

    return sectionAssembler.toSectionBatchesResponse(batchProducts, product, HttpStatus.OK);
  }
}
