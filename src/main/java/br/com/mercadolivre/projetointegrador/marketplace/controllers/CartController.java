package br.com.mercadolivre.projetointegrador.marketplace.controllers;

import br.com.mercadolivre.projetointegrador.marketplace.dtos.PurchaseOrderDTO;
import br.com.mercadolivre.projetointegrador.marketplace.exceptions.InvalidStatusCodeException;
import br.com.mercadolivre.projetointegrador.marketplace.exceptions.NotFoundException;
import br.com.mercadolivre.projetointegrador.marketplace.model.Cart;
import br.com.mercadolivre.projetointegrador.marketplace.services.CartService;
import br.com.mercadolivre.projetointegrador.warehouse.model.AppUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/marketplace/fresh-products/orders")
public class CartController {

  CartService cartService;

  @PostMapping
  public ResponseEntity<BigDecimal> createOrUpdatePurchaseOrder(
      @RequestBody PurchaseOrderDTO purchaseOrderDTO,
      UriComponentsBuilder uriBuilder,
      Authentication authentication)
      throws JsonProcessingException {
    AppUser requestUser = (AppUser) authentication.getPrincipal();
    BigDecimal totalPrice =
        cartService.updateCart(requestUser.getId(), purchaseOrderDTO.mountCart()).getTotalPrice();

    URI uri =
        uriBuilder
            .path("/api/v1/marketplace/fresh-products/orders/{id}")
            .buildAndExpand(requestUser.getId())
            .toUri();

    return ResponseEntity.created(uri).body(totalPrice);
  }

  @GetMapping("/{buyerId}")
  public ResponseEntity<Cart> showOrder(@PathVariable Long buyerId)
      throws NotFoundException, JsonProcessingException {
    return ResponseEntity.ok(cartService.getCart(buyerId));
  }

  @GetMapping
  public ResponseEntity<Cart> showOrderAuth(Authentication authentication)
      throws NotFoundException, JsonProcessingException {
    AppUser requestUser = (AppUser) authentication.getPrincipal();
    return ResponseEntity.ok(cartService.getCart(requestUser.getId()));
  }

  @PatchMapping("/status")
  public ResponseEntity<Cart> switchOrder(Authentication authentication)
      throws NotFoundException, JsonProcessingException {
    AppUser requestUser = (AppUser) authentication.getPrincipal();
    return ResponseEntity.ok(cartService.switchStatus(requestUser.getId()));
  }

  @PutMapping
  public ResponseEntity<Cart> updateOrderStatus(
      @RequestParam String status, Authentication authentication)
      throws JsonProcessingException, NotFoundException, InvalidStatusCodeException {
    AppUser requestUser = (AppUser) authentication.getPrincipal();
    return ResponseEntity.ok(cartService.changeStatus(requestUser.getId(), status));
  }
}
