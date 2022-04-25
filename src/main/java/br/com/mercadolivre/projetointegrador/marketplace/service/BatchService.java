package br.com.mercadolivre.projetointegrador.marketplace.service;

import br.com.mercadolivre.projetointegrador.marketplace.exception.NotFoundException;
import br.com.mercadolivre.projetointegrador.marketplace.model.Batch;
import br.com.mercadolivre.projetointegrador.marketplace.repository.BatchRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BatchService {

    BatchRepository batchRepository;

    public void createBatch(Batch batch) { batchRepository.save(batch); }

    public List<Batch> findAll() {
        return batchRepository.findAll();
    }

    public Batch findById(Long id) throws NotFoundException {
        Optional<Batch> optionalBatch = batchRepository.findById(id);
        if (optionalBatch.isEmpty()) {
            throw new NotFoundException("Lote não encontrado");
        }
        return optionalBatch.get();
    }

    public void updateBatch(Long id, Batch updatedBatch) throws NotFoundException {
        Batch batch = findById(id);

        batch.setBatch_number(updatedBatch.getBatch_number());
        batch.setPrice(updatedBatch.getPrice());
        batch.setDue_date(updatedBatch.getDue_date());
        batch.setManufacturing_datetime(updatedBatch.getManufacturing_datetime());
        batch.setProduct(updatedBatch.getProduct());
        batch.setOrder_number(updatedBatch.getOrder_number());
        batch.setSection_id(updatedBatch.getSeller_id());
        batch.setSeller_id(updatedBatch.getSeller_id());
        batch.setQuantity(updatedBatch.getQuantity());

        batchRepository.save(batch);
    }
}