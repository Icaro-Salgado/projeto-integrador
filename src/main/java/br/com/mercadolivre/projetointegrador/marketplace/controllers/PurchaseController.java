package br.com.mercadolivre.projetointegrador.marketplace.controllers;

import br.com.mercadolivre.projetointegrador.marketplace.dtos.CreatePurchaseDTO;
import br.com.mercadolivre.projetointegrador.marketplace.model.Purchase;
import br.com.mercadolivre.projetointegrador.marketplace.services.PurchaseService;
import br.com.mercadolivre.projetointegrador.security.model.AppUser;
import br.com.mercadolivre.projetointegrador.security.service.TokenService;
import br.com.mercadolivre.projetointegrador.warehouse.docs.config.SecuredMarketplaceRestController;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/customers/marketplace/purchases")
public class PurchaseController implements SecuredMarketplaceRestController {

  PurchaseService purchaseService;
  TokenService tokenService;

  @PostMapping
  public void createPurchase(
      @RequestBody List<CreatePurchaseDTO> createPurchaseDTO, Authentication authentication) {
    AppUser requestUser = (AppUser) authentication.getPrincipal();

    purchaseService.createMultiplePurchases(createPurchaseDTO, requestUser.getId());
  }

  @GetMapping
  public List<Purchase> listCustomerPurchases(Authentication authentication) {
    AppUser requestUser = (AppUser) authentication.getPrincipal();

    return purchaseService.listAllPurchases(requestUser.getId());
  }
}
