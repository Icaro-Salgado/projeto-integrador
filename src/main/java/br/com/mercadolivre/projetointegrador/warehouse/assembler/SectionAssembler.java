package br.com.mercadolivre.projetointegrador.warehouse.assembler;

import br.com.mercadolivre.projetointegrador.warehouse.controller.BatchControllerWarehouse;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.BatchResponseDTO;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.SectionBatchesDTO;
import br.com.mercadolivre.projetointegrador.warehouse.controller.SectionControllerWarehouse;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.SectionResponseDTO;
import br.com.mercadolivre.projetointegrador.warehouse.mapper.BatchMapper;
import br.com.mercadolivre.projetointegrador.warehouse.mapper.SectionMapper;
import br.com.mercadolivre.projetointegrador.warehouse.model.Batch;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import br.com.mercadolivre.projetointegrador.warehouse.utils.ResponseUtils;
import org.springframework.hateoas.Links;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SectionAssembler {

  public ResponseEntity<SectionResponseDTO> toResponse(Section entity, HttpStatus status) {
    SectionResponseDTO dto = SectionMapper.INSTANCE.toDto(entity);

    Links links =
        Links.of(
            linkTo(methodOn(SectionControllerWarehouse.class).findById(entity.getId()))
                .withSelfRel());

    dto.setLinks(List.of(ResponseUtils.parseLinksToMap(links)));

    return new ResponseEntity<>(dto, status);
  }

  public ResponseEntity<SectionBatchesDTO> toSectionBatchesResponse(
      List<Batch> entities, Long productId, HttpStatus status) {
    if (entities.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(new SectionBatchesDTO(null, null, productId, Collections.emptyList()));
    }

    Section section = entities.get(0).getSection();

    SectionBatchesDTO response =
        new SectionBatchesDTO(
            section.getWarehouse().getId().toString(),
            section.getId(),
            productId,
            entities.stream()
                .map(
                    p -> {
                      BatchResponseDTO dto = BatchMapper.INSTANCE.toResponseDTO(p);

                      Links links =
                          Links.of(
                              linkTo(
                                      methodOn(BatchControllerWarehouse.class)
                                          .findBatchById(dto.getId()))
                                  .withSelfRel());
                      dto.setLinks(List.of(ResponseUtils.parseLinksToMap(links)));
                      return dto;
                    })
                .collect(Collectors.toList()));

    return new ResponseEntity<>(response, status);
  }
}
