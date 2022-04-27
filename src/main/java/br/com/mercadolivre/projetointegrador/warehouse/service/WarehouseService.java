package br.com.mercadolivre.projetointegrador.warehouse.service;

import br.com.mercadolivre.projetointegrador.marketplace.exception.NotFoundException;
import br.com.mercadolivre.projetointegrador.marketplace.model.Batch;

import br.com.mercadolivre.projetointegrador.warehouse.enums.SortTypeEnum;
import br.com.mercadolivre.projetointegrador.warehouse.model.InboundOrder;

import br.com.mercadolivre.projetointegrador.marketplace.service.BatchService;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final SectionService sectionService;
    private final BatchService batchService;


    public List<Batch> findProductOnManagerSection(Long managerId, Long productId, SortTypeEnum sortType) throws RuntimeException, NotFoundException {
        Section managerSection = sectionService.findSectionByManager(managerId);

        return batchService.findBatchesByProductAndSection(productId, managerSection);
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
