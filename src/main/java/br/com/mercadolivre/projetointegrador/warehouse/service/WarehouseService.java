package br.com.mercadolivre.projetointegrador.warehouse.service;

import br.com.mercadolivre.projetointegrador.marketplace.exception.NotFoundException;
import br.com.mercadolivre.projetointegrador.marketplace.model.Batch;
import br.com.mercadolivre.projetointegrador.marketplace.service.BatchService;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.WarehouseNotFoundException;
import br.com.mercadolivre.projetointegrador.warehouse.model.InboundOrder;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import br.com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import br.com.mercadolivre.projetointegrador.warehouse.repository.SectionRepository;
import br.com.mercadolivre.projetointegrador.warehouse.repository.WarehouseRepository;
import br.com.mercadolivre.projetointegrador.warehouse.service.validators.WarehouseValidatorExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final SectionRepository sectionRepository;
    private final WarehouseRepository warehouseRepository;
    private final BatchService batchService;
    private final WarehouseValidatorExecutor warehouseValidatorExecutor;

    public Warehouse createWarehouse(Warehouse warehouse){
        return warehouseRepository.save(warehouse);
    }

    public Warehouse findWarehouse(final Long id){
        return warehouseRepository.findById(id).orElseThrow(() -> new WarehouseNotFoundException("Warehouse não encontrada."));
    }

    public List<Batch> saveBatchInSection(InboundOrder inboundOrder) throws NotFoundException {
        //TODO: validar inboundOrder

        List<Batch> addedBatches = new ArrayList<>();

        // TODO: isso tem que ser alguma das validações do validador
        Optional<Section> id = sectionRepository.findById(inboundOrder.getSectionCode());

        if (id.isPresent()) {
            for (Batch batch : inboundOrder.getBatches()) {
                batch.setSection_id(inboundOrder.getSectionCode());
                addedBatches.add(batch);
                batchService.createBatch(batch);
            }
        }

        return addedBatches;
    }

    public List<Batch> updateBatchInSection(InboundOrder inboundOrder) throws NotFoundException {
        warehouseValidatorExecutor.executeValidators(inboundOrder);

        List<Batch> addedBatches = new ArrayList<>();

        Optional<Section> id = sectionRepository.findById(inboundOrder.getSectionCode());

        if (id.isPresent()) {
            for (Batch batch : inboundOrder.getBatches()) {

                addedBatches.add(batch);
                batchService.createBatch(batch);
            }
            return addedBatches;
        } else {
            return null;
        }
    }
}
