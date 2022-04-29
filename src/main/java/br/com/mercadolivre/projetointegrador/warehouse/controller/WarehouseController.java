package br.com.mercadolivre.projetointegrador.warehouse.controller;

import br.com.mercadolivre.projetointegrador.warehouse.assembler.WarehouseAssembler;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.CreateWarehousePayloadDTO;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.BatchStockDTO;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.SectionBatchesDTO;
import br.com.mercadolivre.projetointegrador.warehouse.enums.SortTypeEnum;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.NotFoundException;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.WarehouseResponseDTO;
import br.com.mercadolivre.projetointegrador.warehouse.mapper.WarehouseMapper;
import br.com.mercadolivre.projetointegrador.warehouse.model.AppUser;
import br.com.mercadolivre.projetointegrador.warehouse.model.Batch;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import br.com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import br.com.mercadolivre.projetointegrador.warehouse.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/warehouse")
public class WarehouseController {

  private final WarehouseService warehouseService;
  private final WarehouseAssembler assembler;

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

  @GetMapping("/fresh-products/list")
  public ResponseEntity<?> listStockProducts(
      @RequestParam(required = false) Long product,
      @RequestParam(required = false, defaultValue = "L") SortTypeEnum sort,
      Authentication authentication)
      throws NotFoundException {
    AppUser requestUser = (AppUser) authentication.getPrincipal();
    Long managerId = requestUser.getId();

    if (product == null) {
      throw new IllegalArgumentException();
    }

    List<Batch> batchProducts =
        warehouseService.findProductOnManagerSection(managerId, product, sort);

    if (batchProducts.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Section section = batchProducts.get(0).getSection();

    SectionBatchesDTO response =
        new SectionBatchesDTO(
            section.getWarehouse().getId().toString(),
            section.getId(),
            product,
            batchProducts.stream()
                .map(p -> new BatchStockDTO(p.getBatchNumber(), p.getQuantity(), p.getDue_date()))
                .collect(Collectors.toList()));

    return ResponseEntity.ok(response);
  }
}
