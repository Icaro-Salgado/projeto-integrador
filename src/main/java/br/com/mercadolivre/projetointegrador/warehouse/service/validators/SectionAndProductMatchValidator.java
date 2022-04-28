package br.com.mercadolivre.projetointegrador.warehouse.service.validators;

import br.com.mercadolivre.projetointegrador.warehouse.model.Batch;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.SectionDoesNotMatchWithProductException;
import br.com.mercadolivre.projetointegrador.warehouse.model.InboundOrder;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import br.com.mercadolivre.projetointegrador.warehouse.repository.SectionRepository;

import java.util.List;
import java.util.stream.Collectors;

public class SectionAndProductMatchValidator implements WarehouseValidator {

  private final InboundOrder order;
  private final SectionRepository sectionRepository;

  public SectionAndProductMatchValidator(InboundOrder o, SectionRepository r) {
    this.order = o;
    this.sectionRepository = r;
  }

  @Override
  public void Validate() {
    Section orderSection = sectionRepository.findById(order.getSectionCode()).get();

    List<Batch> invalidProduct =
        order.getBatches().stream()
            .filter(p -> !p.getProduct().getCategory().equals(orderSection.getProduct_category()))
            .collect(Collectors.toList());
    if (!invalidProduct.isEmpty()) {
      String invalidCategories =
          invalidProduct.stream()
              .map(b -> b.getProduct().getCategory().toString())
              .collect(Collectors.joining(" "));
      throw new SectionDoesNotMatchWithProductException(
          "A seção aceita a categoria  "
              + orderSection.getProduct_category()
              + " mas foram encontrados as seguintes categorias "
              + invalidCategories);
    }
  }
}
