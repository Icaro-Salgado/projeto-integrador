package br.com.mercadolivre.projetointegrador.warehouse.service.validators;

import br.com.mercadolivre.projetointegrador.marketplace.repository.BatchRepository;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.SectionNotFoundException;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.SectionTotalCapacityException;
import br.com.mercadolivre.projetointegrador.warehouse.model.InboundOrder;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import br.com.mercadolivre.projetointegrador.warehouse.repository.SectionRepository;

import java.util.stream.Collectors;

public class SectionCapacityValidator implements WarehouseValidator {

  private final InboundOrder order;
  private final SectionRepository sectionRepository;
    private final BatchRepository batchRepository;

    public SectionCapacityValidator(
            InboundOrder inboundOrder,
            SectionRepository sectionRepository,
            BatchRepository batchRepository) {
        this.order = inboundOrder;
        this.sectionRepository = sectionRepository;
        this.batchRepository = batchRepository;
    }

  @Override
  public void Validate() {
    Section orderSection =
        sectionRepository
            .findById(order.getSectionCode())
            .orElseThrow(() -> new SectionNotFoundException("Setor não encontrado!"));
    Integer sectionCapacity = orderSection.getCapacity();

    // To check the capacity, we need to find all batches that have been registered in a given section
    Integer batchesQty = batchRepository.findAll()
            .stream()
            .filter(batch -> batch.getSection_id().equals(orderSection.getId()))
            .collect(Collectors.toList())
            .size();

    if (sectionCapacity < batchesQty + order.getBatches().size())
      throw new SectionTotalCapacityException("A capacidade do setor foi atingida!");
  }
}
