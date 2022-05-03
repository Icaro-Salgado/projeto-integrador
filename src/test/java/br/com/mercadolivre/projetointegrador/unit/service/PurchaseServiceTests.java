package br.com.mercadolivre.projetointegrador.unit.service;

import br.com.mercadolivre.projetointegrador.marketplace.dtos.CreatePurchaseDTO;
import br.com.mercadolivre.projetointegrador.marketplace.model.Ad;
import br.com.mercadolivre.projetointegrador.marketplace.model.AdPurchase;
import br.com.mercadolivre.projetointegrador.marketplace.model.Purchase;
import br.com.mercadolivre.projetointegrador.marketplace.repository.PurchaseRepository;
import br.com.mercadolivre.projetointegrador.marketplace.services.AdService;
import br.com.mercadolivre.projetointegrador.marketplace.services.PurchaseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PurchaseServiceTests {

  @Mock PurchaseRepository purchaseRepository;

  @Mock AdService adService;

  @InjectMocks PurchaseService purchaseService;

  @Test
  @DisplayName(
      "Given an valid order, when call createPurchase, then purchaseRepository.saveAllAndFlush"
          + " should be called with right arguments")
  public void createPurchase() {
    List<CreatePurchaseDTO> adsOrder = new ArrayList<>();
    adsOrder.add(new CreatePurchaseDTO(1L, 10));
    adsOrder.add(new CreatePurchaseDTO(2L, 5));
    adsOrder.add(new CreatePurchaseDTO(3L, 7));

    List<Purchase> purchasesToBeSaved = new ArrayList<>();
    Ad ad = new Ad();
    Mockito.when(adService.findAdById(Mockito.any())).thenReturn(ad);

    for (CreatePurchaseDTO item : adsOrder) {
      Purchase purchase = new Purchase();
      AdPurchase adPurchase = new AdPurchase();
      purchase.setBuyerId(10L);

      adPurchase.setAd(ad);
      adPurchase.setQuantity(item.getQuantity());
      adPurchase.setDiscount(ad.getDiscount());
      adPurchase.setPrice(ad.getPrice());

      purchasesToBeSaved.add(purchase);
    }

    purchaseService.createMultiplePurchases(adsOrder, 10L);

    Mockito.verify(purchaseRepository, Mockito.times(1)).saveAllAndFlush(purchasesToBeSaved);
  }

  @Test
  @DisplayName(
      "Given an valid customer id, when call listAllPurchases, then should call"
          + " purchaseRepository.findAllByBuyerId with this customer id")
  public void listPurchases() {
    purchaseService.listAllPurchases(10L);

    Mockito.verify(purchaseRepository, Mockito.times(1)).findAllByBuyerId(10L);
  }
}
