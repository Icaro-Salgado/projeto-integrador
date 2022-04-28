package br.com.mercadolivre.projetointegrador.warehouse.service.validators;

import br.com.mercadolivre.projetointegrador.marketplace.model.Batch;
import br.com.mercadolivre.projetointegrador.marketplace.repository.BatchRepository;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.BatchAlreadyExists;
import br.com.mercadolivre.projetointegrador.warehouse.model.InboundOrder;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BatchDuplicatedValidator implements WarehouseValidator {

    private final InboundOrder inboundOrder;
    private final BatchRepository batchRepository;


    @Override
    public void Validate() {
        List<Integer> batchesId = inboundOrder.getBatches().stream().map(Batch::getBatchNumber).collect(Collectors.toList());
        List<Batch> foundBatch = batchRepository.findAllByBatchNumberIn(batchesId);

        if (!foundBatch.isEmpty()) {
            List<Integer> numbersFound = foundBatch.stream().map(Batch::getBatchNumber).collect(Collectors.toList());
            String duplicatedNumbers = batchesId.stream().filter(numbersFound::contains).map(Object::toString).collect(Collectors.joining());

            String message = foundBatch.size() > 1 ? "Os lotes " + duplicatedNumbers + " já estão cadastrados." : "O lote " + duplicatedNumbers + " já está cadastrado.";
            throw new BatchAlreadyExists(message);
        }
    }
}
