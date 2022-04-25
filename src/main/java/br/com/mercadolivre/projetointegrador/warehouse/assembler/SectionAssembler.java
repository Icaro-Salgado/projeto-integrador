package br.com.mercadolivre.projetointegrador.warehouse.assembler;

import br.com.mercadolivre.projetointegrador.warehouse.controller.SectionController;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.SectionResponseDTO;
import br.com.mercadolivre.projetointegrador.warehouse.mapper.SectionMapper;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import br.com.mercadolivre.projetointegrador.warehouse.utils.ResponseUtils;
import org.springframework.hateoas.Links;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SectionAssembler {


    public ResponseEntity<SectionResponseDTO> toResponse(Section entity) {
        SectionResponseDTO dto = SectionMapper.INSTANCE.toDto(entity);

        Links links = Links.of(
                linkTo(methodOn(SectionController.class).findById(entity.getId())).withSelfRel()
        );

        dto.setLinks(List.of(ResponseUtils.parseLinksToMap(links)));

        return ResponseEntity.ok(dto);
    }


}
