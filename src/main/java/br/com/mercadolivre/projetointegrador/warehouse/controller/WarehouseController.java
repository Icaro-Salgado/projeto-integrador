package br.com.mercadolivre.projetointegrador.warehouse.controller;

import br.com.mercadolivre.projetointegrador.warehouse.dto.request.CreateWarehousePayloadDTO;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.BatchStockDTO;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.SectionBatchesDTO;
import br.com.mercadolivre.projetointegrador.warehouse.enums.SortTypeEnum;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.NotFoundException;
import br.com.mercadolivre.projetointegrador.warehouse.mapper.WarehouseMapper;
import br.com.mercadolivre.projetointegrador.warehouse.model.Batch;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import br.com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import br.com.mercadolivre.projetointegrador.warehouse.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/warehouse")
public class WarehouseController {

  private final WarehouseService warehouseService;

  @PostMapping
  public ResponseEntity<?> createWarehouse(
      @RequestBody @Valid CreateWarehousePayloadDTO payloadDTO) {
    Warehouse created =
        warehouseService.createWarehouse(WarehouseMapper.INSTANCE.createDtoToModel(payloadDTO));

    return new ResponseEntity<>(created.getId(), HttpStatus.CREATED);
  }

  @GetMapping("/fresh-products/list")
  public ResponseEntity<?> listStockProducts(
          @RequestParam(required = false) Long product,
          @RequestParam(required = false, defaultValue = "L") SortTypeEnum sort
  ) throws NotFoundException {
    // TODO: change for Authentication object from spring security
    Long managerId = 1L;

    if(product == null) {
      throw new IllegalArgumentException();
    }

    List<Batch> batchProducts = warehouseService.findProductOnManagerSection(managerId, product, sort);

    if(batchProducts.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Section section = batchProducts.get(0).getSection();

    SectionBatchesDTO response = new SectionBatchesDTO(
            section.getWarehouse().getId().toString(),
            section.getId(),
            product,
            batchProducts.stream().map(p -> new BatchStockDTO(p.getBatchNumber(), p.getQuantity(), p.getDue_date())).collect(Collectors.toList())

    );


    return ResponseEntity.ok(response);
  }
}
