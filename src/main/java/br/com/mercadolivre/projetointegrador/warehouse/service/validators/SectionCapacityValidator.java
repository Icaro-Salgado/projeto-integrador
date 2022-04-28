package br.com.mercadolivre.projetointegrador.warehouse.service.validators;

import br.com.mercadolivre.projetointegrador.marketplace.model.Batch;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.SectionNotFoundException;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.SectionTotalCapacityException;
import br.com.mercadolivre.projetointegrador.warehouse.model.InboundOrder;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import br.com.mercadolivre.projetointegrador.warehouse.repository.SectionRepository;

public class SectionCapacityValidator implements WarehouseValidator {

    private final InboundOrder order;
    private final SectionRepository sectionRepository;

    public SectionCapacityValidator(InboundOrder o, SectionRepository r) {
        this.order = o;
        this.sectionRepository = r;
    }

    @Override
    public void Validate() {
        Section orderSection =
                sectionRepository
                        .findById(order.getSectionCode())
                        .orElseThrow(() -> new SectionNotFoundException("Section Not Found!"));
        Integer sectionCapacity = orderSection.getCapacity();

        Integer productsQty =
                order.getBatches().stream().map(Batch::getQuantity).reduce(0, Integer::sum);

        if (sectionCapacity < productsQty)
            throw new SectionTotalCapacityException("The Section capacity has been reached!!");
    }
}
