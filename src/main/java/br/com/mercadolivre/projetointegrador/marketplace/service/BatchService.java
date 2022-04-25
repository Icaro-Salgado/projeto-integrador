package br.com.mercadolivre.projetointegrador.marketplace.service;

import br.com.mercadolivre.projetointegrador.marketplace.model.Batch;
import br.com.mercadolivre.projetointegrador.marketplace.repository.BatchRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BatchService {

    BatchRepository batchRepository;

    public void createBatch(Batch batch) { batchRepository.save(batch); }

    public List<Batch> findAll() {
        return batchRepository.findAll();
    }
}
