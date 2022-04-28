package br.com.mercadolivre.projetointegrador.warehouse.service.validators;

import br.com.mercadolivre.projetointegrador.warehouse.exception.db.SectionNotFoundException;
import br.com.mercadolivre.projetointegrador.warehouse.exception.validators.InboundOrderInvalidManagerException;
import br.com.mercadolivre.projetointegrador.warehouse.model.InboundOrder;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import br.com.mercadolivre.projetointegrador.warehouse.repository.SectionRepository;

public class SectionManagerIdValidator implements WarehouseValidator{

    private final InboundOrder inboundOrder;
    private final SectionRepository sectionRepository;

    public SectionManagerIdValidator(InboundOrder inboundOrder, SectionRepository sectionRepository) {
        this.inboundOrder = inboundOrder;
        this.sectionRepository = sectionRepository;
    }

    @Override
    public void Validate() {
        Section section = sectionRepository.findById(inboundOrder.getSectionCode()).orElseThrow(() -> new SectionNotFoundException("Section Not Found!"));

        if(!inboundOrder.getManagerId().equals(section.getManagerId())){
            throw new InboundOrderInvalidManagerException("Seu usuário não está associado a seção informada");
        }

    }
}
