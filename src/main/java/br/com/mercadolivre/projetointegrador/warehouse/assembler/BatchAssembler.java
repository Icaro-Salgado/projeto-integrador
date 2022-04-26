package br.com.mercadolivre.projetointegrador.warehouse.assembler;

import br.com.mercadolivre.projetointegrador.marketplace.model.Batch;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.CreatedBatchDTO;
import br.com.mercadolivre.projetointegrador.warehouse.mapper.BatchMapper;
import br.com.mercadolivre.projetointegrador.warehouse.utils.ResponseUtils;
import org.springframework.hateoas.Links;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BatchAssembler {

    public ResponseEntity<List<CreatedBatchDTO>> toCreatedResponse(List<Batch> createdBatches) {

        List<CreatedBatchDTO> createdBatchesDTO = createdBatches
                .stream()
                .map(b -> BatchMapper.INSTANCE.toCreatedDTO(b))
                .collect(Collectors.toList());


        for (CreatedBatchDTO dto : createdBatchesDTO) {
            Links links = Links.of(); // TODO: Fazer os links apontando para o BatchController

            dto.setLinks(List.of(ResponseUtils.parseLinksToMap(links)));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(createdBatchesDTO);
    }
}
