package br.com.mercadolivre.projetointegrador.marketplace.services;

import br.com.mercadolivre.projetointegrador.marketplace.dtos.CreateOrUpdateAdDTO;
import br.com.mercadolivre.projetointegrador.marketplace.exceptions.OutOfStockException;
import br.com.mercadolivre.projetointegrador.marketplace.exceptions.UnauthorizedException;
import br.com.mercadolivre.projetointegrador.marketplace.model.Ad;
import br.com.mercadolivre.projetointegrador.marketplace.model.AdBatch;
import br.com.mercadolivre.projetointegrador.marketplace.repository.AdBatchesRepository;
import br.com.mercadolivre.projetointegrador.marketplace.repository.AdRepository;
import br.com.mercadolivre.projetointegrador.warehouse.enums.CategoryEnum;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.NotFoundException;
import br.com.mercadolivre.projetointegrador.warehouse.model.Batch;
import br.com.mercadolivre.projetointegrador.warehouse.repository.BatchRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class AdService {

  AdRepository adRepository;
  BatchRepository batchRepository;
  AdBatchesRepository adBatchesRepository;

  @Transactional(
      rollbackFor = Exception.class,
      propagation = Propagation.REQUIRED,
      isolation = Isolation.SERIALIZABLE)
  public Ad createAd(Long sellerId, CreateOrUpdateAdDTO createAdDTO) {
    Ad ad = createAdDTO.DTOtoModel();
    ad.setSellerId(sellerId);

    List<Integer> batchesId = createAdDTO.getBatchesId();
    Integer quantity = batchRepository.findAllByBatchNumberIn(batchesId).stream()
            .map(Batch::getQuantity)
            .reduce(0, Integer::sum);

    ad.setQuantity(quantity);
    adRepository.save(ad);

    for (Integer id : batchesId) {
      AdBatch adBatch = new AdBatch();
      adBatch.setBatchId(id);
      adBatch.setAd(ad);
      adBatchesRepository.save(adBatch);
    }

    return ad;
  }

  public List<Ad> listAds() {
    return this.adRepository.findAll();
  }

  public List<Ad> listAds(String name) {
    return this.adRepository.findAdsByLikeName(name);
  }

  public List<Ad> listAds(String name, CategoryEnum category) {
    if (name != null && category != null) {
      return this.adRepository.findAllByCategoryAndNameLike(category, name);
    }

    return name != null
        ? this.adRepository.findAdsByLikeName(name)
        : adRepository.findAllByCategory(category);
  }

  public void reduceAdQuantity(Long adId, Integer quantity) throws OutOfStockException {
    Ad ad = findAdById(adId);
    Integer newQuantity = ad.getQuantity() - quantity;
    if (newQuantity < 0) {
      throw new OutOfStockException("Quantidade insuficiente.");
    }
    ad.setQuantity(newQuantity);
    adRepository.save(ad);
  }

  public List<Ad> listAdsByCustomerId(Long id) {
    return this.adRepository.findAllBySellerId(id);
  }

  public Ad findAdById(Long id) {
    Ad ad = this.adRepository.findById(id).orElse(null);
    if (ad == null) {
      throw new NotFoundException("Anúncio não localizado.");
    }
    return ad;
  }

  public void deleteAd(Long customerId, Long adId) throws UnauthorizedException {
    Ad ad = findAdById(adId);
    if (!ad.getSellerId().equals(customerId)) {
      throw new UnauthorizedException("Não é permitido excluir o anúncio de outro usuário.");
    }
    adRepository.delete(ad);
  }
}
