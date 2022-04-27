package br.com.mercadolivre.projetointegrador.warehouse.service.validators;

import br.com.mercadolivre.projetointegrador.marketplace.model.Product;
import br.com.mercadolivre.projetointegrador.marketplace.repository.ProductRepository;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.SectionDoesNotMatchWithProductException;
import br.com.mercadolivre.projetointegrador.warehouse.model.InboundOrder;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import br.com.mercadolivre.projetointegrador.warehouse.repository.SectionRepository;

import java.util.List;
import java.util.stream.Collectors;

public class SectionAndProductMatchValidator implements WarehouseValidator{

    private final InboundOrder order;
    private final SectionRepository sectionRepository;
    private final ProductRepository productRepository;

    public SectionAndProductMatchValidator(InboundOrder o, SectionRepository r, ProductRepository p) {
        this.order = o;
        this.sectionRepository = r;
        this.productRepository = p;
    }

    @Override
    public void Validate() {
        Section orderSection = sectionRepository.findById(order.getSectionCode()).get();

        List<Long> productsId = order.getBatches()
                .stream()
                .map(p-> p.getProduct().getId())
                .collect(Collectors.toList());

        List<Product> productsFromDb = productRepository.findAllById(productsId);


        for(int i = 0; i < productsFromDb.size(); i++){
            Product element = productsFromDb.get(i);

            if (!element.getCategory().equals(orderSection.getProduct_category())){

                String msg = "The Product(" + element.getName()
                        + ")"
                        + " category("
                        + element.getCategory()
                        + ")"
                        + " category does not match with the section("+orderSection.getProduct_category()+")";
                throw new SectionDoesNotMatchWithProductException(msg);
            }
            break;
        }



    }
}
