package br.com.mercadolivre.projetointegrador.warehouse.controller;

import br.com.mercadolivre.projetointegrador.warehouse.assembler.ProductAssembler;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.CreateOrUpdateProductDTO;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.ProductDTO;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.InvalidCategoryException;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.NotFoundException;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.ProductAlreadyExists;
import br.com.mercadolivre.projetointegrador.warehouse.model.Product;
import br.com.mercadolivre.projetointegrador.warehouse.service.ProductService;
import br.com.mercadolivre.projetointegrador.warehouse.view.ProductView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/fresh-products")
public class ProductController {

  private final ProductService productService;
  private final ProductAssembler productAssembler;

  @PostMapping
  public ResponseEntity<ProductDTO> createProduct(
      @Valid @RequestBody CreateOrUpdateProductDTO createOrUpdateProductDTO,
      UriComponentsBuilder uriBuilder)
      throws InvalidCategoryException, ProductAlreadyExists {
    Product product = createOrUpdateProductDTO.mountProduct();
    productService.createProduct(product);

    URI uri =
        uriBuilder.path("/api/v1/fresh-products/{id}").buildAndExpand(product.getId()).toUri();

    HttpHeaders headers = new HttpHeaders();
    headers.add("Location", uri.toString());

    return productAssembler.toResponse(product,HttpStatus.CREATED, headers);
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
  @JsonView(ProductView.Detail.class)
  public ResponseEntity<ProductDTO> getById(@PathVariable Long id) throws NotFoundException {
    Product product = productService.findById(id);

    return productAssembler.toResponse(product, HttpStatus.OK);
  }

  @GetMapping
  @JsonView(ProductView.List.class)
  public ResponseEntity<List<ProductDTO>> getAll() {
    List<Product> products = productService.findAll();
    return productAssembler.toResponse(products, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> exclude(@PathVariable Long id) throws NotFoundException {
    productService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
