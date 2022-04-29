package br.com.mercadolivre.projetointegrador.warehouse.controller;

import br.com.mercadolivre.projetointegrador.warehouse.assembler.SectionAssembler;
import br.com.mercadolivre.projetointegrador.warehouse.assembler.WarehouseAssembler;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.CreateWarehousePayloadDTO;
import br.com.mercadolivre.projetointegrador.warehouse.enums.SortTypeEnum;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.NotFoundException;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.WarehouseResponseDTO;
import br.com.mercadolivre.projetointegrador.warehouse.mapper.WarehouseMapper;
import br.com.mercadolivre.projetointegrador.warehouse.model.AppUser;
import br.com.mercadolivre.projetointegrador.warehouse.model.Batch;
import br.com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import br.com.mercadolivre.projetointegrador.warehouse.service.WarehouseService;
import br.com.mercadolivre.projetointegrador.warehouse.view.SectionView;
import com.fasterxml.jackson.annotation.JsonView;
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
public class WarehouseController {

  private final WarehouseService warehouseService;
  private final WarehouseAssembler assembler;
  private final SectionAssembler sectionAssembler;

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
  @JsonView(SectionView.SectionBatches.class)
  public ResponseEntity<?> listStockProducts(
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
