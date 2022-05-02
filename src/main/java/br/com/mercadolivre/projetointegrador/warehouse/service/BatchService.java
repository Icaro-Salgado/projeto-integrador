package br.com.mercadolivre.projetointegrador.warehouse.service;

import br.com.mercadolivre.projetointegrador.warehouse.exception.db.NotFoundException;
import br.com.mercadolivre.projetointegrador.warehouse.model.Batch;
import br.com.mercadolivre.projetointegrador.warehouse.repository.BatchRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BatchService {

  private final BatchRepository batchRepository;
  private final ProductService productService;

  @Value("${ad.minimumWeeks}")
  private Integer minimumWeeksToAnnounce;

  public void createBatch(Batch batch) throws NotFoundException {
    productService.findById(batch.getProduct().getId());

    batchRepository.save(batch);
  }

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
    batchRepository.save(buildUpdatedBatch(batch, updatedBatch));
  }

  public Batch updateBatchByBatchNumber(Batch updatedBatch) {
    Integer batchNumber = updatedBatch.getBatchNumber();
    Batch batch =
        batchRepository
            .findByBatchNumber(batchNumber)
            .orElseThrow(
                () ->
                    new NotFoundException(
                        "Lote com o número " + batchNumber + " não foi encontrado"));

    return batchRepository.save(buildUpdatedBatch(batch, updatedBatch));
  }

  public void delete(Long id) throws NotFoundException {
    Batch batch = findById(id);

    batchRepository.delete(batch);
  }

  private Batch buildUpdatedBatch(Batch batch, Batch updatedBatch) {

    batch.setBatchNumber(updatedBatch.getBatchNumber());
    batch.setPrice(updatedBatch.getPrice());
    batch.setDueDate(updatedBatch.getDueDate());
    batch.setManufacturing_datetime(updatedBatch.getManufacturing_datetime());
    batch.setProduct(updatedBatch.getProduct());
    batch.setOrder_number(updatedBatch.getOrder_number());
    batch.setSection_id(updatedBatch.getSection_id());
    batch.setSeller(updatedBatch.getSeller());
    batch.setQuantity(updatedBatch.getQuantity());

    return batch;
  }

  public List<Batch> listBySellerId(final Long sellerId){
    LocalDate date = LocalDate.now().plusWeeks(minimumWeeksToAnnounce);

    return batchRepository.findAllBySellerIdAndDueDateGreaterThan(sellerId, date);
  }
}
