package br.com.mercadolivre.projetointegrador.marketplace.controllers;

import br.com.mercadolivre.projetointegrador.marketplace.dtos.CreateOrUpdateAdDTO;
import br.com.mercadolivre.projetointegrador.marketplace.exceptions.UnauthorizedException;
import br.com.mercadolivre.projetointegrador.marketplace.model.Ad;
import br.com.mercadolivre.projetointegrador.marketplace.services.AdService;
import br.com.mercadolivre.projetointegrador.warehouse.model.AppUser;
import br.com.mercadolivre.projetointegrador.warehouse.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ads")
@AllArgsConstructor
public class AdController {

  AdService adService;
  AppUserRepository tokenService;

  @PostMapping
  public ResponseEntity<Void> createAd(
      @Valid @RequestBody CreateOrUpdateAdDTO createOrUpdateAdDTO,
      UriComponentsBuilder uriBuilder,
      Authentication authentication) {
    AppUser requestUser = (AppUser) authentication.getPrincipal();
    Ad ad = adService.createAd(requestUser.getId(), createOrUpdateAdDTO);
    URI uri = uriBuilder.path("/api/v1/ad/{id}").buildAndExpand(ad.getId()).toUri();

    return ResponseEntity.created(uri).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Ad> findAd(@PathVariable Long id) {
    return ResponseEntity.ok(adService.findAdById(id));
  }

  @GetMapping
  public ResponseEntity<List<Ad>> listAds(@RequestParam(required = false) String name) {
    if (name != null) {
      return ResponseEntity.ok(adService.listAds(name));
    }

    return ResponseEntity.ok(adService.listAds());
  }

  @GetMapping("/seller")
  public ResponseEntity<List<Ad>> listCustomerAds(Authentication authentication) {
    AppUser requestUser = (AppUser) authentication.getPrincipal();
    List<Ad> sellerAds = adService.listAdsByCustomerId(requestUser.getId());

    return ResponseEntity.ok(sellerAds);
  }

  @DeleteMapping("/{adId}/delete")
  public ResponseEntity<Void> deleteAd(@PathVariable Long adId, Authentication authentication)
      throws UnauthorizedException {
    AppUser requestUser = (AppUser) authentication.getPrincipal();
    adService.deleteAd(requestUser.getId(), adId);

    return ResponseEntity.noContent().build();
  }
}
