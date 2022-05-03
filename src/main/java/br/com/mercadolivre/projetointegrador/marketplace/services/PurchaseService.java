package br.com.mercadolivre.projetointegrador.marketplace.services;

import br.com.mercadolivre.projetointegrador.marketplace.dtos.CartProductDTO;
import br.com.mercadolivre.projetointegrador.marketplace.dtos.PurchaseProductResponseDTO;
import br.com.mercadolivre.projetointegrador.marketplace.dtos.PurchaseResponseDTO;
import br.com.mercadolivre.projetointegrador.marketplace.exceptions.NotFoundException;
import br.com.mercadolivre.projetointegrador.marketplace.model.Ad;
import br.com.mercadolivre.projetointegrador.marketplace.model.AdPurchase;
import br.com.mercadolivre.projetointegrador.marketplace.model.Cart;
import br.com.mercadolivre.projetointegrador.marketplace.model.Purchase;
import br.com.mercadolivre.projetointegrador.marketplace.repository.AdPurchaseRepository;
import br.com.mercadolivre.projetointegrador.marketplace.repository.PurchaseRepository;
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
  CartService cartService;
  AdPurchaseRepository adPurchaseRepository;
  PurchaseRepository purchaseRepository;

  @Transactional(
          rollbackFor = Exception.class,
          propagation = Propagation.REQUIRED,
          isolation = Isolation.SERIALIZABLE)
  public void createPurchase(Long buyerId) throws NotFoundException, JsonProcessingException {
    Cart cart = cartService.getCart(buyerId);

    Purchase purchase = new Purchase();
    List<AdPurchase> adPurchases = new ArrayList<>();

    purchase.setBuyerId(buyerId);
    purchase.setStatusCode("ABERTO");
    purchase.setTotal(cart.getTotalPrice());

    for (CartProductDTO product : cart.getProducts()) {
      Ad ad = adService.findAdById(product.getProductId());

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

  public PurchaseResponseDTO listAllPurchases(Long customerId) throws NotFoundException {
    Purchase purchase = purchaseRepository.findByBuyerId(customerId).orElse(null);
    if (purchase == null) {
      throw new NotFoundException("Compra não localizada.");
    }

    List<AdPurchase> adPurchases = adPurchaseRepository.findAllByPurchase(purchase);

    List<PurchaseProductResponseDTO> products = adPurchases.stream().map(p -> {
      Ad ad = p.getAd();
      return new PurchaseProductResponseDTO(ad.getName(), ad.getPrice(), ad.getQuantity(), ad.getCategory());
    }).collect(Collectors.toList());

    PurchaseResponseDTO response = new PurchaseResponseDTO();
    response.setPurchaseId(purchase.getId());
    response.setStatusCode(purchase.getStatusCode());
    response.setProducts(products);
    response.setTotal(purchase.getTotal());

    return response;
  }
}
