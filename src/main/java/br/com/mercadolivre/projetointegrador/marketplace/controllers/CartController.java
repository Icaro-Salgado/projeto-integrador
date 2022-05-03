package br.com.mercadolivre.projetointegrador.marketplace.controllers;

import br.com.mercadolivre.projetointegrador.marketplace.dtos.PurchaseOrderDTO;
import br.com.mercadolivre.projetointegrador.marketplace.dtos.PurchaseOrderResponseDTO;
import br.com.mercadolivre.projetointegrador.marketplace.exceptions.InvalidStatusCodeException;
import br.com.mercadolivre.projetointegrador.marketplace.exceptions.NotFoundException;
import br.com.mercadolivre.projetointegrador.marketplace.model.Cart;
import br.com.mercadolivre.projetointegrador.marketplace.services.CartService;
import br.com.mercadolivre.projetointegrador.warehouse.exception.ErrorDTO;
import br.com.mercadolivre.projetointegrador.warehouse.model.AppUser;
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

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/fresh-products/orders")
@Tag(name = "Purchase Order")
public class CartController {

  CartService cartService;

  @Operation(summary = "SALVA UM CARRINHO NO COMPRAS", description = "Cria um carrinho de compras")
  @ApiResponses(
      value = {
        @ApiResponse(
            description = "Carrinho criado.",
            responseCode = "201",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = PurchaseOrderResponseDTO.class))
            })
      })
  @PostMapping
  public ResponseEntity<PurchaseOrderResponseDTO> createOrUpdatePurchaseOrder(
      @RequestBody PurchaseOrderDTO purchaseOrderDTO,
      UriComponentsBuilder uriBuilder,
      Authentication authentication)
      throws JsonProcessingException {
    AppUser requestUser = (AppUser) authentication.getPrincipal();
    PurchaseOrderResponseDTO totalPrice = new PurchaseOrderResponseDTO();
    totalPrice.setTotalPrice(
        cartService.updateCart(requestUser.getId(), purchaseOrderDTO.mountCart()).getTotalPrice());

    URI uri =
        uriBuilder
            .path("/api/v1/fresh-products/orders/{id}")
            .buildAndExpand(requestUser.getId())
            .toUri();

    return ResponseEntity.created(uri).body(totalPrice);
  }

  @Operation(
      summary = "RETORNA UM CARRINHO DE COMPRAS",
      description = "Retorna o carrinho de compras do comprador com id informado.")
  @ApiResponses(
      value = {
        @ApiResponse(
            description = "Carrinho encontrado.",
            responseCode = "200",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Cart.class))
            }),
        @ApiResponse(
            description = "Carrinho não encontrado.",
            responseCode = "404",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorDTO.class))
            })
      })
  @GetMapping("/{buyerId}")
  public ResponseEntity<Cart> showOrder(@PathVariable Long buyerId)
      throws NotFoundException, JsonProcessingException {
    return ResponseEntity.ok(cartService.getCart(buyerId));
  }
}
