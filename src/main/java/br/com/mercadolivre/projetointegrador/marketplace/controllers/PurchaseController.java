package br.com.mercadolivre.projetointegrador.marketplace.controllers;

import br.com.mercadolivre.projetointegrador.marketplace.dtos.PurchaseResponseDTO;
import br.com.mercadolivre.projetointegrador.marketplace.exceptions.NotFoundException;
import br.com.mercadolivre.projetointegrador.marketplace.exceptions.UnauthorizedException;
import br.com.mercadolivre.projetointegrador.marketplace.model.Purchase;
import br.com.mercadolivre.projetointegrador.marketplace.services.CartService;
import br.com.mercadolivre.projetointegrador.marketplace.services.PurchaseService;
import br.com.mercadolivre.projetointegrador.security.model.AppUser;
import br.com.mercadolivre.projetointegrador.security.service.TokenService;
import br.com.mercadolivre.projetointegrador.warehouse.docs.config.SecuredMarketplaceRestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/customers/marketplace/purchases")
@Tag(name = "[Marketplace] - Purchase")
public class PurchaseController implements SecuredMarketplaceRestController {
  PurchaseService purchaseService;
  TokenService tokenService;
  CartService cartService;

  @Operation(summary = "SALVA UMA COMPRA", description = "Registra uma compra.")
  @ApiResponses(
      value = {
        @ApiResponse(description = "Compra registrada.", responseCode = "201"),
        @ApiResponse(description = "Carrinho não encontrado", responseCode = "404")
      })
  @PostMapping
  public ResponseEntity<Void> createPurchase(
      Authentication authentication, UriComponentsBuilder uriBuilder)
      throws NotFoundException, JsonProcessingException {
    AppUser requestUser = (AppUser) authentication.getPrincipal();

    purchaseService.createPurchase(requestUser.getId());
    cartService.clearCart(requestUser.getId());

    URI uri =
        uriBuilder
            .path("/api/v1/customers/marketplace/purchases/{id}")
            .buildAndExpand(requestUser.getId())
            .toUri();
    return ResponseEntity.created(uri).build();
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
  @GetMapping("/{id}")
  public ResponseEntity<List<PurchaseResponseDTO>> listCustomerPurchases(@PathVariable Long id) {
    return ResponseEntity.ok(purchaseService.listAllPurchases(id));
  }

  @Operation(
      summary = "ALTERA O STATUS DE UMA COMPRA",
      description = "Modifica o Status da compra com id informado na URL.")
  @ApiResponses(
      value = {
        @ApiResponse(description = "Compra finalizada", responseCode = "200"),
        @ApiResponse(description = "Compra não encontrada", responseCode = "404"),
        @ApiResponse(description = "Não autorizado", responseCode = "403")
      })
  @PutMapping("/{buyerId}/{purchaseId}")
  public ResponseEntity<PurchaseResponseDTO> updatePurchaseStatus(
      @PathVariable Long buyerId, @PathVariable Long purchaseId)
      throws NotFoundException, UnauthorizedException {
    PurchaseResponseDTO purchaseResponse = purchaseService.changeStatus(purchaseId, buyerId);
    return ResponseEntity.ok(purchaseResponse);
  }
}
