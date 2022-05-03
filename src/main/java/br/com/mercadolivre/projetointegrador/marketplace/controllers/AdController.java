package br.com.mercadolivre.projetointegrador.marketplace.controllers;

import br.com.mercadolivre.projetointegrador.marketplace.dtos.CreateOrUpdateAdDTO;
import br.com.mercadolivre.projetointegrador.marketplace.exceptions.UnauthorizedException;
import br.com.mercadolivre.projetointegrador.marketplace.model.Ad;
import br.com.mercadolivre.projetointegrador.marketplace.services.AdService;
import br.com.mercadolivre.projetointegrador.security.model.AppUser;
import br.com.mercadolivre.projetointegrador.security.repository.AppUserRepository;
import br.com.mercadolivre.projetointegrador.warehouse.docs.config.SecuredMarketplaceRestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/marketplace/ads")
@AllArgsConstructor
@Tag(name = "[Marketplace] - Ad")
public class AdController implements SecuredMarketplaceRestController {
  AdService adService;
  AppUserRepository tokenService;

  @Operation(summary = "CRIA UM ANÚNCIO", description = "Cria um anúncio relacionado a um produto")
  @ApiResponses(
      value = {
        @ApiResponse(description = "Anúncio criado.", responseCode = "201"),
        @ApiResponse(
            description = "Um ou mais atributos da Anúncio não foi preenchido.",
            responseCode = "400")
      })
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

  @Operation(
      summary = "RETORNA UM ANÚNCIO",
      description = "Retorna um anúncio com id correspondente ao passado na url")
  @ApiResponses(
      value = {
        @ApiResponse(description = "Anúncio encontrado.", responseCode = "200"),
        @ApiResponse(description = "Anúncio não localizado.", responseCode = "404")
      })
  @GetMapping("/{id}")
  public ResponseEntity<Ad> findAd(@PathVariable Long id) {
    return ResponseEntity.ok(adService.findAdById(id));
  }

  @Operation(
      summary = "RETORNA LISTA DE ANÚNCIOS CADASTRADOS",
      description = "Retorna lista de anúncios cadastrados.")
  @ApiResponses(value = {@ApiResponse(description = "Anúncios encontrados.", responseCode = "200")})
  @GetMapping
  public ResponseEntity<List<Ad>> listAds(@RequestParam(required = false) String name) {
    if (name != null) {
      return ResponseEntity.ok(adService.listAds(name));
    }

    return ResponseEntity.ok(adService.listAds());
  }

  @Operation(
      summary = "RETORNA ANÚNCIOS DE UM VENDEDOR",
      description = "Retorna lista de anúncios cadastrados pelo vendedor autenticado.")
  @ApiResponses(value = {@ApiResponse(description = "Anúncios encontrados.", responseCode = "200")})
  @GetMapping("/seller")
  public ResponseEntity<List<Ad>> listCustomerAds(Authentication authentication) {
    AppUser requestUser = (AppUser) authentication.getPrincipal();
    List<Ad> sellerAds = adService.listAdsByCustomerId(requestUser.getId());

    return ResponseEntity.ok(sellerAds);
  }

  @Operation(
      summary = "EXCLUI UM ANÚNCIO",
      description = "Exclui o anúncio com id correspondente ao passado na url.")
  @ApiResponses(value = {@ApiResponse(description = "Anúncio deletado.", responseCode = "204")})
  @DeleteMapping("/{adId}/delete")
  public ResponseEntity<Void> deleteAd(@PathVariable Long adId, Authentication authentication)
      throws UnauthorizedException {
    AppUser requestUser = (AppUser) authentication.getPrincipal();
    adService.deleteAd(requestUser.getId(), adId);

    return ResponseEntity.noContent().build();
  }
}
