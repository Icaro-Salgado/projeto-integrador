package br.com.mercadolivre.projetointegrador.warehouse.controller;

import br.com.mercadolivre.projetointegrador.warehouse.assembler.WarehouseAssembler;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.CreateWarehousePayloadDTO;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.WarehouseResponseDTO;
import br.com.mercadolivre.projetointegrador.warehouse.mapper.WarehouseMapper;
import br.com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import br.com.mercadolivre.projetointegrador.warehouse.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    headers.add("Location", linkTo(methodOn(WarehouseController.class).findById(created.getId())).withSelfRel().toString());

    return assembler.toResponse(created, HttpStatus.CREATED, headers);
  }

  @GetMapping("/{id}")
  public ResponseEntity<WarehouseResponseDTO> findById(@PathVariable Long id){
    Warehouse warehouse = warehouseService.findWarehouse(id);

    return assembler.toResponse(warehouse, HttpStatus.CREATED, null);
  }
}
