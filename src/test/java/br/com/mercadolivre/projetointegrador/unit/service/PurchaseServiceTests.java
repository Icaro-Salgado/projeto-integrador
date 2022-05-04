package br.com.mercadolivre.projetointegrador.unit.service;

import br.com.mercadolivre.projetointegrador.marketplace.dtos.CartProductDTO;
import br.com.mercadolivre.projetointegrador.marketplace.dtos.PurchaseResponseDTO;
import br.com.mercadolivre.projetointegrador.marketplace.enums.PurchaseStatusCodeEnum;
import br.com.mercadolivre.projetointegrador.marketplace.exceptions.NotFoundException;
import br.com.mercadolivre.projetointegrador.marketplace.exceptions.OutOfStockException;
import br.com.mercadolivre.projetointegrador.marketplace.exceptions.UnauthorizedException;
import br.com.mercadolivre.projetointegrador.marketplace.model.Ad;
import br.com.mercadolivre.projetointegrador.marketplace.model.AdPurchase;
import br.com.mercadolivre.projetointegrador.marketplace.model.Cart;
import br.com.mercadolivre.projetointegrador.marketplace.model.Purchase;
import br.com.mercadolivre.projetointegrador.marketplace.repository.AdBatchesRepository;
import br.com.mercadolivre.projetointegrador.marketplace.repository.AdPurchaseRepository;
import br.com.mercadolivre.projetointegrador.marketplace.repository.PurchaseRepository;
import br.com.mercadolivre.projetointegrador.marketplace.services.AdService;
import br.com.mercadolivre.projetointegrador.marketplace.services.CartService;
import br.com.mercadolivre.projetointegrador.marketplace.services.PurchaseService;
import br.com.mercadolivre.projetointegrador.warehouse.service.BatchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PurchaseServiceTests {

  @Mock AdPurchaseRepository adPurchaseRepository;

  @Mock
  AdBatchesRepository adBatchesRepository;

  @Mock
  BatchService batchService;

  @Mock PurchaseRepository purchaseRepository;

  @Mock AdService adService;

  @Mock CartService cartService;

  @InjectMocks PurchaseService purchaseService;

  @Test
  @DisplayName(
      "Given an valid order, when call createPurchase, then purchaseRepository.save and"
          + " adPurchaseRepository.saveAll should be called with right arguments")
  public void createPurchase() throws NotFoundException, JsonProcessingException, OutOfStockException {
    Cart mockCart = new Cart();
    List<CartProductDTO> products = new ArrayList<>();
    products.add(new CartProductDTO(1L, 10, BigDecimal.valueOf(11.90)));
    products.add(new CartProductDTO(2L, 6, BigDecimal.valueOf(1.00)));
    mockCart.setTotalPrice(BigDecimal.valueOf(125.00));
    mockCart.setProducts(products);
    Mockito.when(cartService.getCart(Mockito.any())).thenReturn(mockCart);

    Purchase purchase = new Purchase();
    purchase.setBuyerId(10L);
    purchase.setStatusCode(PurchaseStatusCodeEnum.ABERTO);
    purchase.setTotal(mockCart.getTotalPrice());

    List<AdPurchase> adPurchases = new ArrayList<>();
    Ad mockAd = new Ad();
    mockAd.setDiscount(5);

    Mockito.when(adService.findAdById(Mockito.any())).thenReturn(mockAd);

    for (CartProductDTO product : mockCart.getProducts()) {
      Ad ad = adService.findAdById(product.getProductId());

      AdPurchase adPurchase = new AdPurchase();
      adPurchase.setAd(ad);
      adPurchase.setQuantity(product.getQuantity());
      adPurchase.setDiscount(ad.getDiscount());
      adPurchase.setPurchase(purchase);

      adPurchases.add(adPurchase);
    }

    purchaseService.createPurchase(10L);
    Mockito.verify(adBatchesRepository, Mockito.times(2)).findAllByAd(Mockito.any());
    Mockito.verify(batchService, Mockito.times(2)).reduceBatchQuantity(Mockito.any(), Mockito.any());
    Mockito.verify(adPurchaseRepository, Mockito.times(1)).saveAll(adPurchases);
    Mockito.verify(purchaseRepository, Mockito.times(1)).save(purchase);
  }

  @Test
  @DisplayName(
      "Given an valid customer id, when call listAllPurchases, then should call"
          + " purchaseRepository.findAllByBuyerId with this customer id")
  public void listPurchases() {
    Purchase purchase = new Purchase();
    purchase.setBuyerId(10L);
    purchase.setStatusCode(PurchaseStatusCodeEnum.ABERTO);
    purchase.setTotal(BigDecimal.valueOf(11.90));

    Mockito.when(purchaseRepository.findAllByBuyerId(Mockito.any())).thenReturn(List.of(purchase));
    Mockito.when(adPurchaseRepository.findAllByPurchase(Mockito.any()))
        .thenReturn(new ArrayList<>());

    purchaseService.listAllPurchases(10L);

    Mockito.verify(purchaseRepository, Mockito.times(1)).findAllByBuyerId(10L);
    Mockito.verify(adPurchaseRepository, Mockito.times(1)).findAllByPurchase(purchase);
  }

  @Test
  @DisplayName(
      "Given a new status code, when call changeStatus, then the statusCode should be updated.")
  public void shouldUpdateStatusCode() throws NotFoundException, UnauthorizedException {

    Purchase purchase = new Purchase();
    purchase.setBuyerId(10L);
    purchase.setStatusCode(PurchaseStatusCodeEnum.ABERTO);
    purchase.setTotal(BigDecimal.valueOf(11.90));

    Mockito.when(purchaseRepository.findById(Mockito.any()))
        .thenReturn(java.util.Optional.of(purchase));

    PurchaseResponseDTO purchaseResponse = purchaseService.changeStatus(1L, 10L);

    Assertions.assertEquals(PurchaseStatusCodeEnum.FINALIZADO, purchaseResponse.getStatusCode());
  }
}
