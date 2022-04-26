package br.com.mercadolivre.projetointegrador.warehouse.service;

import br.com.mercadolivre.projetointegrador.marketplace.exception.NotFoundException;
import br.com.mercadolivre.projetointegrador.marketplace.model.Batch;

import br.com.mercadolivre.projetointegrador.warehouse.model.InboundOrder;

import br.com.mercadolivre.projetointegrador.marketplace.service.BatchService;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final SectionService sectionService;
    private final BatchService batchService;


    public List<Batch> findProductOnManagerSection(Long managerId, Long productId, String sortType) throws RuntimeException, NotFoundException {
        Section managerSection = sectionService.findSectionByManager(managerId);
        List<Batch> productBatches = batchService.findProductBatches(productId);

        List<Batch> foundedProducts = productBatches
                .stream()
                .filter(b -> b.getSection().getId().equals(managerSection.getId()))
                .collect(Collectors.toList());

        return foundedProducts;
    }

    public List<Object> saveBatchInSection(InboundOrder inboundOrder) {

        //TODO: validar inboundOrder
        //TODO: salvar inboundOrder
        //TODO: retornar lotes salvos

        //TODO: Mudar para list<Batch>
        List<Object> addedBatches = new ArrayList<>();

        for (Object batch : inboundOrder.getBatches()) {
            //TODO: setar id da section
            addedBatches.add(batch);
            //TODO: addedBatches.add(batchService.add(batch))
        }


        return addedBatches;
    }
}
