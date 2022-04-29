package br.com.mercadolivre.projetointegrador.marketplace.controllers;

import br.com.mercadolivre.projetointegrador.marketplace.model.Ad;
import br.com.mercadolivre.projetointegrador.marketplace.services.AdService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ad")
@AllArgsConstructor
public class AdController {

  AdService adService;

  @PostMapping
  public ResponseEntity<Void> createAd(@Valid @RequestBody Ad ad, UriComponentsBuilder uriBuilder) {
    adService.createAd(ad);
    URI uri = uriBuilder.path("/api/v1/ad/{id}").buildAndExpand(ad.getId()).toUri();

    return ResponseEntity.created(uri).build();
  }

  @GetMapping("/seller/{seller_id}")
  public ResponseEntity<List<Ad>> listCustomerAds(@PathVariable Long seller_id) {
    List<Ad> sellerAds = adService.listAdsByCustomerId(seller_id);
    return ResponseEntity.ok(sellerAds);
  }
}
