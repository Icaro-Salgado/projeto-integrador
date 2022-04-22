package br.com.mercadolivre.projetointegrador.warehouse.assembler;

import br.com.mercadolivre.projetointegrador.warehouse.controller.SectionController;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SectionAssembler implements RepresentationModelAssembler<Section, EntityModel<Section>> {
    @Override
    public EntityModel<Section> toModel(Section entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(SectionController.class).findById(entity.getId())).withSelfRel()
        );
    }
}
