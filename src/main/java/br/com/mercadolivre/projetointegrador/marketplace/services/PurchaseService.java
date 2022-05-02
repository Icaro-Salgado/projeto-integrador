package br.com.mercadolivre.projetointegrador.marketplace.services;

import br.com.mercadolivre.projetointegrador.marketplace.dtos.CreatePurchaseDTO;
import br.com.mercadolivre.projetointegrador.marketplace.model.Ad;
import br.com.mercadolivre.projetointegrador.marketplace.model.AdPurchase;
import br.com.mercadolivre.projetointegrador.marketplace.model.Purchase;
import br.com.mercadolivre.projetointegrador.marketplace.repository.PurchaseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PurchaseService {

    AdService adService;
    PurchaseRepository purchaseRepository;

    public void createMultiplePurchases(List<CreatePurchaseDTO> adId, Long buyer_id) {
        List<Purchase> purchasesToBeSaved = new ArrayList<>();

        for (CreatePurchaseDTO item: adId) {
            Ad ad = adService.findAdById(item.getAdId());

            Purchase purchase = new Purchase();
            AdPurchase adPurchase = new AdPurchase();
            purchase.setBuyerId(buyer_id);

            adPurchase.setAd(ad);
            adPurchase.setQuantity(item.getQuantity());
            adPurchase.setDiscount(ad.getDiscount());
            adPurchase.setPrice(ad.getPrice());

            purchasesToBeSaved.add(purchase);
        }

        purchaseRepository.saveAllAndFlush(purchasesToBeSaved);
    }

    public List<Purchase> listAllPurchases(Long customerId) {
        return purchaseRepository.findAllByBuyerId(customerId);
    }

}
