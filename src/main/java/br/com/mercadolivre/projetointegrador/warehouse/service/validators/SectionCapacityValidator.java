package br.com.mercadolivre.projetointegrador.warehouse.service.validators;

import br.com.mercadolivre.projetointegrador.warehouse.repository.BatchRepository;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.SectionNotFoundException;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.SectionTotalCapacityException;
import br.com.mercadolivre.projetointegrador.warehouse.model.InboundOrder;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import br.com.mercadolivre.projetointegrador.warehouse.repository.SectionRepository;

import java.util.List;
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

    int batchesQty = batchRepository.findAllBySection_IdIn(List.of(order.getSectionCode())).size();

    if (sectionCapacity < batchesQty + order.getBatches().size()){
      int availableSpaces = Math.max((sectionCapacity - batchesQty), 0);

      throw new SectionTotalCapacityException(
              "A capacidade do setor foi atingida! A ordem de registro contem "
                      + order.getBatches().size()
                      + " lotes, mas o setor comporta apenas "
                      + availableSpaces
                      + " novos lotes");
    }

  }
}
