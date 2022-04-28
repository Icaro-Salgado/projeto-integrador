package br.com.mercadolivre.projetointegrador.warehouse.service;

import br.com.mercadolivre.projetointegrador.marketplace.exception.NotFoundException;
import br.com.mercadolivre.projetointegrador.marketplace.model.Batch;
import br.com.mercadolivre.projetointegrador.marketplace.repository.BatchRepository;
import br.com.mercadolivre.projetointegrador.marketplace.service.BatchService;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.WarehouseNotFoundException;
import br.com.mercadolivre.projetointegrador.warehouse.model.InboundOrder;
import br.com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import br.com.mercadolivre.projetointegrador.warehouse.repository.WarehouseRepository;
import br.com.mercadolivre.projetointegrador.warehouse.service.validators.BatchDuplicatedValidator;
import br.com.mercadolivre.projetointegrador.warehouse.service.validators.WarehouseValidatorExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final BatchService batchService;
    private final BatchRepository batchRepository;
    private final WarehouseValidatorExecutor warehouseValidatorExecutor;

    public Warehouse createWarehouse(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    public Warehouse findWarehouse(final Long id) {
        return warehouseRepository.findById(id).orElseThrow(() -> new WarehouseNotFoundException("Warehouse não encontrada."));
    }

    public List<Batch> saveBatchInSection(InboundOrder inboundOrder) throws NotFoundException {

        warehouseValidatorExecutor.executeValidators(inboundOrder, List.of(
                new BatchDuplicatedValidator(inboundOrder, batchRepository)
                )
        );

        List<Batch> addedBatches = new ArrayList<>();

        for (Batch batch : inboundOrder.getBatches()) {
            addedBatches.add(batch);
            batchService.createBatch(batch);
        }

        return addedBatches;
    }

    public List<Batch> updateBatchInSection(InboundOrder inboundOrder) throws NotFoundException {
        warehouseValidatorExecutor.executeValidators(inboundOrder);
        List<Batch> addedBatches = new ArrayList<>();

        for (Batch batch : inboundOrder.getBatches()) {
            addedBatches.add(batchService.updateBatchByBatchNumber(batch));
        }
        return addedBatches;

    }
}
