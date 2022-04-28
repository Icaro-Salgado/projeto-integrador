package br.com.mercadolivre.projetointegrador.warehouse.service.validators;

import br.com.mercadolivre.projetointegrador.marketplace.model.Batch;
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
//    private final ProductRepository productRepository;

    public SectionAndProductMatchValidator(InboundOrder o, SectionRepository r) {
        this.order = o;
        this.sectionRepository = r;
    }

    @Override
    public void Validate() {
        Section orderSection = sectionRepository.findById(order.getSectionCode()).get();

        List<Long> productsId = order.getBatches()
                .stream()
                .map(p-> p.getProduct().getId())
                .collect(Collectors.toList());

//        List<Product> productsFromDb = productRepository.findAllById(productsId);
        List<Batch> invalidProduct = order.getBatches().stream().filter(p -> !p.getProduct().getCategory().equals(orderSection.getProduct_category())).collect(Collectors.toList());
        if(!invalidProduct.isEmpty()) {
            String invalidCategories = invalidProduct.stream().map(b -> b.getProduct().getCategory().toString()).collect(Collectors.joining());
            throw new SectionDoesNotMatchWithProductException("A seção aceita a categoria  " + orderSection.getProduct_category() + " mas foram encontrados as seguintes categorias" + invalidCategories);
        }
//
//        order.getBatches().get(0).getProduct().getCategory().equals(orderSection.getProduct_category());
//
//
//        for(int i = 0; i < productsFromDb.size(); i++){
//            Product element = productsFromDb.get(i);
//
//            if (!element.getCategory().equals(orderSection.getProduct_category())){
//
//                String msg = "The Product(" + element.getName()
//                        + ")"
//                        + " category("
//                        + element.getCategory()
//                        + ")"
//                        + " category does not match with the section("+orderSection.getProduct_category()+")";
//                throw new SectionDoesNotMatchWithProductException(msg);
//            }
//            break;
//        }



    }
}
