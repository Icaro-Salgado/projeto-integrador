package br.com.mercadolivre.projetointegrador.warehouse.assembler;

import br.com.mercadolivre.projetointegrador.warehouse.controller.WarehouseController;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.WarehouseResponseDTO;
import br.com.mercadolivre.projetointegrador.warehouse.mapper.WarehouseMapper;
import br.com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import br.com.mercadolivre.projetointegrador.warehouse.utils.ResponseUtils;
import org.springframework.hateoas.Links;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class WarehouseAssembler {

  public ResponseEntity<WarehouseResponseDTO> toResponse(
      Warehouse entity, HttpStatus status, HttpHeaders headers) {
    WarehouseResponseDTO dto = WarehouseMapper.INSTANCE.toResponseDTO(entity);

    Links links =
        Links.of(
            linkTo(methodOn(WarehouseController.class).findById(entity.getId())).withSelfRel());

    dto.setLinks(List.of(ResponseUtils.parseLinksToMap(links)));

    return new ResponseEntity<>(dto, headers, status);
  }
}
