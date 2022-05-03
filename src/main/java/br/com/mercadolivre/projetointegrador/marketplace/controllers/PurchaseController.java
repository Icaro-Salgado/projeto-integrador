package br.com.mercadolivre.projetointegrador.marketplace.controllers;

import br.com.mercadolivre.projetointegrador.marketplace.dtos.CreatePurchaseDTO;
import br.com.mercadolivre.projetointegrador.marketplace.model.Purchase;
import br.com.mercadolivre.projetointegrador.marketplace.services.PurchaseService;
import br.com.mercadolivre.projetointegrador.security.model.AppUser;
import br.com.mercadolivre.projetointegrador.security.service.TokenService;
import br.com.mercadolivre.projetointegrador.warehouse.docs.config.SecuredMarketplaceRestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/customers/marketplace/purchases")
@Tag(name = "[Marketplace] - Purchase")
public class PurchaseController implements SecuredMarketplaceRestController {
  PurchaseService purchaseService;
  TokenService tokenService;

  @Operation(summary = "SALVA UMA COMPRA", description = "Registra uma compra.")
  @ApiResponses(value = {@ApiResponse(description = "Compra registrada.", responseCode = "201")})
  @PostMapping
  public ResponseEntity<Void> createPurchase(
      @RequestBody List<CreatePurchaseDTO> createPurchaseDTO, Authentication authentication) {
    AppUser requestUser = (AppUser) authentication.getPrincipal();

    purchaseService.createMultiplePurchases(createPurchaseDTO, requestUser.getId());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @Operation(summary = "RETORNA UMA COMPRA", description = "Retorna uma compra.")
  @ApiResponses(
      value = {
        @ApiResponse(
            description = "Compra encontrada.",
            responseCode = "200",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Purchase.class))
            })
      })
  @GetMapping
  public List<Purchase> listCustomerPurchases(Authentication authentication) {
    AppUser requestUser = (AppUser) authentication.getPrincipal();

    return purchaseService.listAllPurchases(requestUser.getId());
  }
}
