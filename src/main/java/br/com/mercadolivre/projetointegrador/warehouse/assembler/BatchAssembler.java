package br.com.mercadolivre.projetointegrador.warehouse.assembler;

import br.com.mercadolivre.projetointegrador.warehouse.controller.BatchController;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.BatchResponseDTO;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.NotFoundException;
import br.com.mercadolivre.projetointegrador.warehouse.model.Batch;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.CreatedBatchDTO;
import br.com.mercadolivre.projetointegrador.warehouse.mapper.BatchMapper;
import br.com.mercadolivre.projetointegrador.warehouse.utils.ResponseUtils;
import org.springframework.hateoas.Links;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BatchAssembler {

  public ResponseEntity<List<CreatedBatchDTO>> toCreatedResponse(List<Batch> createdBatches)
      throws NotFoundException {

    List<CreatedBatchDTO> createdBatchesDTO =
        createdBatches.stream()
            .map(BatchMapper.INSTANCE::toCreatedDTO)
            .collect(Collectors.toList());

    for (CreatedBatchDTO dto : createdBatchesDTO) {
      Links links =
          Links.of(
              linkTo(methodOn(BatchController.class).findBatchById(dto.getId())).withSelfRel());

      dto.setLinks(List.of(ResponseUtils.parseLinksToMap(links)));
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(createdBatchesDTO);
  }

  public ResponseEntity<List<BatchResponseDTO>> toBatchResponse(
      List<Batch> batchList, HttpStatus status) {
    List<BatchResponseDTO> batchResponseDTOList = BatchMapper.INSTANCE.toResponseDTO(batchList);

    batchResponseDTOList.forEach(
        batch -> {
          Links links =
              Links.of(
                  linkTo(methodOn(BatchController.class).findBatchById(batch.getId()))
                      .withSelfRel());

          batch.setLinks(List.of(ResponseUtils.parseLinksToMap(links)));
        });

    return ResponseEntity.status(status).body(batchResponseDTOList);
  }
}
