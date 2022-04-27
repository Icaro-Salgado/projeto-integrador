package br.com.mercadolivre.projetointegrador.warehouse.service.validators;

import br.com.mercadolivre.projetointegrador.warehouse.exception.db.SectionTotalCapacityException;
import br.com.mercadolivre.projetointegrador.warehouse.model.InboundOrder;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import br.com.mercadolivre.projetointegrador.warehouse.repository.SectionRepository;

public class SectionCapacityValidator implements WarehouseValidator{

    private final InboundOrder order;
    private final SectionRepository sectionRepository;

    public SectionCapacityValidator(InboundOrder o, SectionRepository r) {
        this.order = o;
        this.sectionRepository = r;
    }

    @Override
    public void Validate() {
        Section orderSection = sectionRepository.findById(order.getSectionCode()).get();
        Integer sectionCapacity = orderSection.getCapacity();
        Integer batchesQty = order.getBatches().size();

        if(sectionCapacity < batchesQty) throw new SectionTotalCapacityException("The Section capacity has been reached!!");
    }
}
