package br.com.mercadolivre.projetointegrador.warehouse.service.validators;

import br.com.mercadolivre.projetointegrador.warehouse.model.InboundOrder;
import br.com.mercadolivre.projetointegrador.warehouse.repository.SectionRepository;
import br.com.mercadolivre.projetointegrador.warehouse.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WarehouseValidatorExecutor {

    @Autowired
    private  SectionRepository sectionRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;


    public void executeValidators(InboundOrder inboundOrder){
        List<WarehouseValidator> validators = buildValidators(inboundOrder);

        validators.forEach(WarehouseValidator::Validate);
    }

    public void executeValidators(InboundOrder inboundOrder, List<WarehouseValidator> additionalValidators){
        List<WarehouseValidator> validators = new java.util.ArrayList<>(buildValidators(inboundOrder));
        validators.addAll(additionalValidators);

        validators.forEach(WarehouseValidator::Validate);
    }

    private List<WarehouseValidator> buildValidators(InboundOrder inboundOrder){
        return List.of(
                new SectionExistsValidator(inboundOrder.getSectionCode(),sectionRepository),
                new WarehouseExistsValidator(inboundOrder.getWarehouseCode(), warehouseRepository),
                new SectionCapacityValidator(inboundOrder, sectionRepository),
                new SectionAndProductMatchValidator(inboundOrder, sectionRepository)
        );
    }

}
