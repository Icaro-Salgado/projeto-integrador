package br.com.mercadolivre.projetointegrador.marketplace.services;

import br.com.mercadolivre.projetointegrador.marketplace.dtos.CartProductDTO;
import br.com.mercadolivre.projetointegrador.marketplace.dtos.PurchaseProductResponseDTO;
import br.com.mercadolivre.projetointegrador.marketplace.dtos.PurchaseResponseDTO;
import br.com.mercadolivre.projetointegrador.marketplace.enums.PurchaseStatusCodeEnum;
import br.com.mercadolivre.projetointegrador.marketplace.exceptions.OutOfStockException;
import br.com.mercadolivre.projetointegrador.marketplace.exceptions.NotFoundException;
import br.com.mercadolivre.projetointegrador.marketplace.exceptions.UnauthorizedException;
import br.com.mercadolivre.projetointegrador.marketplace.model.*;
import br.com.mercadolivre.projetointegrador.marketplace.repository.AdBatchesRepository;
import br.com.mercadolivre.projetointegrador.marketplace.repository.AdPurchaseRepository;
import br.com.mercadolivre.projetointegrador.marketplace.repository.PurchaseRepository;
import br.com.mercadolivre.projetointegrador.warehouse.service.BatchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class PurchaseService {

  AdService adService;
  BatchService batchService;
  CartService cartService;
  AdBatchesRepository adBatchesRepository;
  AdPurchaseRepository adPurchaseRepository;
  PurchaseRepository purchaseRepository;

  @Transactional(
      rollbackFor = Exception.class,
      propagation = Propagation.REQUIRED,
      isolation = Isolation.SERIALIZABLE)
  public void createPurchase(Long buyerId) throws NotFoundException, JsonProcessingException, OutOfStockException {
    Cart cart = cartService.getCart(buyerId);

    Purchase purchase = new Purchase();
    List<AdPurchase> adPurchases = new ArrayList<>();

    purchase.setBuyerId(buyerId);
    purchase.setStatusCode(PurchaseStatusCodeEnum.ABERTO);
    purchase.setTotal(cart.getTotalPrice());

    for (CartProductDTO product : cart.getProducts()) {
      Ad ad = adService.findAdById(product.getProductId());
      adService.reduceAdQuantity(product.getProductId(), product.getQuantity());
      List<Integer> batchesIds = adBatchesRepository.findAllByAd(ad).stream().map(AdBatch::getBatchId).collect(Collectors.toList());
      batchService.reduceBatchQuantity(batchesIds, product.getQuantity());

      AdPurchase adPurchase = new AdPurchase();
      adPurchase.setAd(ad);
      adPurchase.setQuantity(product.getQuantity());
      adPurchase.setDiscount(ad.getDiscount());
      adPurchase.setPurchase(purchase);

      adPurchases.add(adPurchase);
    }

    adPurchaseRepository.saveAll(adPurchases);
    purchaseRepository.save(purchase);
  }

  private List<PurchaseProductResponseDTO> getProducts(Purchase purchase) {
    List<AdPurchase> adPurchases = adPurchaseRepository.findAllByPurchase(purchase);

    return adPurchases.stream()
        .map(
            p -> {
              Ad ad = p.getAd();
              return new PurchaseProductResponseDTO(
                  ad.getName(), ad.getPrice(), p.getQuantity(), ad.getCategory());
            })
        .collect(Collectors.toList());
  }

  public List<PurchaseResponseDTO> listAllPurchases(Long customerId) {
    List<Purchase> purchases = purchaseRepository.findAllByBuyerId(customerId);
    List<PurchaseResponseDTO> purchasesResponse = new ArrayList<>();

    for (Purchase purchase : purchases) {
      List<PurchaseProductResponseDTO> products = getProducts(purchase);

      PurchaseResponseDTO purchaseResponse = new PurchaseResponseDTO().modelToDTO(purchase);
      purchaseResponse.setProducts(products);

      purchasesResponse.add(purchaseResponse);
    }

    return purchasesResponse;
  }

  public Purchase findPurchaseById(Long id) throws NotFoundException {
    Purchase purchase = purchaseRepository.findById(id).orElse(null);
    if (purchase == null) {
      throw new NotFoundException("Compra não encontrada.");
    }
    return purchase;
  }

  public PurchaseResponseDTO changeStatus(Long purchaseId, Long buyerId)
      throws NotFoundException, UnauthorizedException {
    Purchase purchase = findPurchaseById(purchaseId);
    if (buyerId != purchase.getBuyerId()) {
      throw new UnauthorizedException("Essa compra não pertence ao usário indicado.");
    }
    purchase.setStatusCode(PurchaseStatusCodeEnum.FINALIZADO);
    purchaseRepository.save(purchase);

    PurchaseResponseDTO purchaseResponse = new PurchaseResponseDTO().modelToDTO(purchase);
    purchaseResponse.setProducts(getProducts(purchase));

    return purchaseResponse;
  }
}
