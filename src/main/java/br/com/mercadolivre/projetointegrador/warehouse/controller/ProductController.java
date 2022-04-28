package br.com.mercadolivre.projetointegrador.warehouse.controller;

import br.com.mercadolivre.projetointegrador.warehouse.dto.request.CreateOrUpdateProductDTO;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.InvalidCategoryException;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.NotFoundException;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.ProductAlreadyExists;
import br.com.mercadolivre.projetointegrador.warehouse.model.Product;
import br.com.mercadolivre.projetointegrador.warehouse.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/fresh-products")
public class ProductController {

  ProductService productService;

  @PostMapping
  public ResponseEntity<Void> createProduct(
      @Valid @RequestBody CreateOrUpdateProductDTO createOrUpdateProductDTO,
      UriComponentsBuilder uriBuilder)
      throws InvalidCategoryException, ProductAlreadyExists {
    Product product = createOrUpdateProductDTO.mountProduct();
    productService.createProduct(product);

    URI uri =
        uriBuilder.path("/api/v1/fresh-products/{id}").buildAndExpand(product.getId()).toUri();

    return ResponseEntity.created(uri).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> updateProduct(
      @PathVariable Long id,
      @Valid @RequestBody CreateOrUpdateProductDTO createOrUpdateProductDTO,
      UriComponentsBuilder uriBuilder)
      throws NotFoundException, InvalidCategoryException {

    Product product = createOrUpdateProductDTO.mountProduct();
    productService.updateProduct(id, product);

    URI uri =
        uriBuilder.path("/api/v1/fresh-products/{id}").buildAndExpand(product.getId()).toUri();

    return ResponseEntity.noContent().location(uri).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Product> getById(@PathVariable Long id) throws NotFoundException {
    Product product = productService.findById(id);

    return ResponseEntity.ok(product);
  }

  @GetMapping
  public ResponseEntity<List<Product>> getAll() {
    List<Product> products = productService.findAll();
    return ResponseEntity.ok(products);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> exclude(@PathVariable Long id) throws NotFoundException {
    productService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
