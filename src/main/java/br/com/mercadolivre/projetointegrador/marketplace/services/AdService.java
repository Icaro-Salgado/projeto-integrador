package br.com.mercadolivre.projetointegrador.marketplace.services;

import br.com.mercadolivre.projetointegrador.marketplace.dtos.CreateOrUpdateAdDTO;
import br.com.mercadolivre.projetointegrador.marketplace.exceptions.UnauthorizedException;
import br.com.mercadolivre.projetointegrador.marketplace.model.Ad;
import br.com.mercadolivre.projetointegrador.marketplace.model.AdBatch;
import br.com.mercadolivre.projetointegrador.marketplace.repository.AdBatchesRepository;
import br.com.mercadolivre.projetointegrador.marketplace.repository.AdRepository;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.NotFoundException;
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
  AdBatchesRepository adBatchesRepository;

  @Transactional(
      rollbackFor = Exception.class,
      propagation = Propagation.REQUIRED,
      isolation = Isolation.SERIALIZABLE)
  public Ad createAd(Long sellerId, CreateOrUpdateAdDTO createAdDTO) {
    Ad ad = new Ad();
    ad.setSellerId(sellerId);
    ad.setName(createAdDTO.getName());
    ad.setQuantity(createAdDTO.getQuantity());
    ad.setPrice(createAdDTO.getPrice());
    ad.setDiscount(createAdDTO.getDiscount());
    ad.setCategory(createAdDTO.getCategory());

    List<Long> batchesId = createAdDTO.getBatchesId();
    adRepository.save(ad);

    for (Long id : batchesId) {
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
    if (ad.getSellerId().equals(customerId)) {
      adRepository.delete(ad);
    }
    throw new UnauthorizedException("Não é permitido excluir o anúncio de outro usuário.");
  }
}
