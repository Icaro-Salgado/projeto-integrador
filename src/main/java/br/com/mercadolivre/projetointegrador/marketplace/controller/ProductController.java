package br.com.mercadolivre.projetointegrador.marketplace.controller;

import br.com.mercadolivre.projetointegrador.marketplace.dto.CreateOrUpdateProductDTO;
import br.com.mercadolivre.projetointegrador.marketplace.exception.NotFoundException;
import br.com.mercadolivre.projetointegrador.marketplace.model.Product;
import br.com.mercadolivre.projetointegrador.marketplace.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/fresh-products")
public class ProductController {

    ProductService productService;

    @PostMapping
    public ResponseEntity<Void> createProduct(
            @Valid @RequestBody CreateOrUpdateProductDTO createOrUpdateProductDTO,
            UriComponentsBuilder uriBuilder
    ) {
        Product product = createOrUpdateProductDTO.mountProduct();
        productService.createProduct(product);

        URI uri = uriBuilder.path("/api/v1/fresh-products/{id}").buildAndExpand(product.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody CreateOrUpdateProductDTO createOrUpdateProductDTO,
            UriComponentsBuilder uriBuilder) throws NotFoundException {

        Product product = createOrUpdateProductDTO.mountProduct();
        productService.updateProduct(id, product);

        URI uri = uriBuilder.path("/api/v1/fresh-products/{id}").buildAndExpand(product.getId()).toUri();

        return ResponseEntity.noContent().location(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) throws NotFoundException {
        Product product = productService.findById(id);

        return ResponseEntity.ok(product);
    }
}
