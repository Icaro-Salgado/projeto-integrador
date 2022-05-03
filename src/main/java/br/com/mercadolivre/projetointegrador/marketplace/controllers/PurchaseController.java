package br.com.mercadolivre.projetointegrador.marketplace.controllers;

import br.com.mercadolivre.projetointegrador.marketplace.dtos.CreatePurchaseDTO;
import br.com.mercadolivre.projetointegrador.marketplace.model.Purchase;
import br.com.mercadolivre.projetointegrador.marketplace.services.PurchaseService;
import br.com.mercadolivre.projetointegrador.warehouse.model.AppUser;
import br.com.mercadolivre.projetointegrador.warehouse.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/customers/purchases")
@Tag(name = "Purchase")
public class PurchaseController {

  PurchaseService purchaseService;
  TokenService tokenService;

  @Operation(summary = "SALVA UMA COMPRA", description = "Registra uma compra.")
  @ApiResponses(
      value = {
        @ApiResponse(
            description = "Compra registrada.",
            responseCode = "201",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CreatePurchaseDTO.class))
            })
      })
  @PostMapping
  public void createPurchase(
      @RequestBody List<CreatePurchaseDTO> createPurchaseDTO, Authentication authentication) {
    AppUser requestUser = (AppUser) authentication.getPrincipal();

    purchaseService.createMultiplePurchases(createPurchaseDTO, requestUser.getId());
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
